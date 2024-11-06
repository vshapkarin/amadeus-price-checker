package ru.vsh.amadeus.price.checker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FlightOffer {

    @JsonProperty("type")
    private String type;

    @JsonProperty("id")
    private String id;

    @JsonProperty("source")
    private String source;

    @JsonProperty("price")
    private Price price;

    @JsonProperty("itineraries")
    private List<Itinerary> itineraries;

}
