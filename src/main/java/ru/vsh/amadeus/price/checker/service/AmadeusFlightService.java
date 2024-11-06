package ru.vsh.amadeus.price.checker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vsh.amadeus.price.checker.model.AmadeusFlightOfferResponse;
import ru.vsh.amadeus.price.checker.model.FlightOffer;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmadeusFlightService {

    private final AmadeusAuthService authService;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    @Value("${amadeus.api.flight_offers_url}")
    private String flightOffersUrl;

    @Value("${flight.origin}")
    private String origin;

    @Value("${flight.destination}")
    private String destination;

    @Value("${flight.departure_date}")
    private String departureDate;

    @Value("${flight.departure_time}")
    private String departureTime;

    @Value("${flight.departure_flight_number}")
    private String departureFlightNumber;

    @SneakyThrows
    public String searchFlights() throws JsonProcessingException {
        final var headers = new HttpHeaders();
        headers.setBearerAuth(authService.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        final var url = String.format("%s?originLocationCode=%s&destinationLocationCode=%s&departureDate=%s&adults=1",
                flightOffersUrl, origin, destination, departureDate);

        final var responseString = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class);

        log.debug("Got JSON response from Amadeus: {}", responseString);

        final var responseJson = objectMapper.readValue(responseString.getBody(), AmadeusFlightOfferResponse.class);

        log.info("Got response from Amadeus: {}", responseJson);

        return responseJson.getFlightOffers().stream()
                .filter(this::isMyFlight)
                .map(FlightOffer::getPrice)
                .map(p -> p.getTotal() + " " + p.getCurrency())
                .findFirst()
                .orElse(null);
    }

    private boolean isMyFlight(FlightOffer flightOffer) {
        final var itineraries = flightOffer.getItineraries();
        for (var itinerary : itineraries) {
            final var segments = itinerary.getSegments();
            for (var segment : segments) {
                final var departure = segment.getDeparture();
                if (departureTime.equals(departure.getAt()) && departureFlightNumber.equals(segment.getFlightNumber())) {
                    return true;
                }
            }
        }
        return false;
    }

}
