package ru.vsh.amadeus.price.checker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Segment {

    @JsonProperty("departure")
    private FlightPoint departure;

    @JsonProperty("arrival")
    private FlightPoint arrival;

    @JsonProperty("carrierCode")
    private String carrierCode;

    @JsonProperty("duration")
    private String duration;

    @JsonProperty("number")
    private String flightNumber;
}
