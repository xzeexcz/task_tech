package kz.solva.task.clientservice.dto.twelvedata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwelveDataResponse {
    private String symbol;
    private String name;
    @JsonProperty("datetime")
    private String dateTime;
    private double close;
    private double previous_close;

    public boolean isValid() {
        return symbol != null && name != null && dateTime != null && previous_close < 0;
    }
}
