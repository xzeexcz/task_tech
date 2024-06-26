package kz.solva.task.transactionsservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AppConfig {
    @Bean
    public ExecutorService executorService(@Value("${threads.pool}") Integer threadsValue) {
        return Executors.newFixedThreadPool(threadsValue);
    }
}
