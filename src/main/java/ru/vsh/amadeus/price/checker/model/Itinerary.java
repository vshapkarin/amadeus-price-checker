package ru.vsh.amadeus.price.checker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Itinerary {

    @JsonProperty("duration")
    private String duration;

    @JsonProperty("segments")
    private List<Segment> segments;

}
