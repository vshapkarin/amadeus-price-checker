package ru.vsh.amadeus.price.checker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Price {

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("total")
    private String total;

    @JsonProperty("base")
    private String base;

}
