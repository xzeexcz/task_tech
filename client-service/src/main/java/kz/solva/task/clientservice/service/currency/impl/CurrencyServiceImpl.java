package kz.solva.task.clientservice.service.currency.impl;

import kz.solva.task.clientservice.api.twelvedata.TwelveDataClient;
import kz.solva.task.clientservice.dto.currency.CurrencyDto;
import kz.solva.task.clientservice.dto.twelvedata.TwelveDataResponse;
import kz.solva.task.clientservice.entity.currency.Currency;
import kz.solva.task.clientservice.mapper.currency.CurrencyMapper;
import kz.solva.task.clientservice.repository.currency.CurrencyRepo;
import kz.solva.task.clientservice.service.currency.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyMapper currencyMapper;
    private final CurrencyRepo currencyRepo;
    private final TwelveDataClient twelveDataClient;
    @Override
    public ResponseEntity<String> addCurrency() throws ExecutionException, InterruptedException {
        List<TwelveDataResponse> responses = twelveDataClient.getQuotes();
        try {
            log.info("Трансформация в сущность");
            List<Currency> currencyList = currencyMapper.toCurrencyEntityList(responses);

            for(Currency currency : currencyList) {
                currency.setId(UUID.randomUUID());
            }
            currencyRepo.saveAll(currencyList);
            log.info("Стоимость курсов валютных пар успешно добавлена");
            return ResponseEntity.ok("Success");
        } catch (RestClientException e) {
            log.atWarn().log(e.getMessage());
            return ResponseEntity.ok("Failed");
        }
    }

    @Override
    public CurrencyDto getCurrencyKzt() throws ExecutionException, InterruptedException {
        List<Currency> currencies = currencyRepo.findBySymbolEquals("USD/KZT");

        if (currencies.isEmpty()) {
            log.info("Установка актуальных курсов.");
            addCurrency();
            currencies = currencyRepo.findBySymbolEquals("USD/KZT");
        }

        Currency newestCurency = currencies.stream()
                .max(Comparator.comparing(Currency:: getDateTime))
                .orElse(null);

        return currencyMapper.toCurrencyDto(newestCurency);
    }

    @Override
    public CurrencyDto getCurrencyRub() throws ExecutionException, InterruptedException {
        List<Currency> currencies = currencyRepo.findBySymbolEquals("RUB/USD");

        if (currencies.isEmpty()) {
            log.info("Установка актуальных курсов.");
            addCurrency();
            currencies = currencyRepo.findBySymbolEquals("RUB/USD");
        }

        Currency newestCurency = currencies.stream()
                .max(Comparator.comparing(Currency:: getDateTime))
                .orElse(null);

        return currencyMapper.toCurrencyDto(newestCurency);
    }

    @Override
    public CurrencyDto getCurrencyPair(String currencyPair) throws ExecutionException, InterruptedException {
        CurrencyDto currencyDto = null;
        if (currencyPair.isEmpty()) {
            return null;
        } else {
            currencyDto = switch (currencyPair) {
                case "USD/KZT" -> getCurrencyKzt();
                case "RUB/USD" -> getCurrencyRub();
                default -> currencyDto;
            };
            return currencyDto;
        }
    }
}
