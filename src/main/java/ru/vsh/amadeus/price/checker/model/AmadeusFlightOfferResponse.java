package ru.vsh.amadeus.price.checker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AmadeusFlightOfferResponse {

    @JsonProperty("data")
    private List<FlightOffer> flightOffers;

}
