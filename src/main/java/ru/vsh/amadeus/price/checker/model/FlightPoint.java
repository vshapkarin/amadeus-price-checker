package ru.vsh.amadeus.price.checker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FlightPoint {

    @JsonProperty("iataCode")
    private String iataCode;

    @JsonProperty("at")
    private String at;

}
