package ru.vsh.amadeus.price.checker.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.vsh.amadeus.price.checker.service.AmadeusFlightService;
import ru.vsh.amadeus.price.checker.service.TelegramService;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmadeusPriceCheckerScheduler {

    private final AmadeusFlightService flightService;

    private final TelegramService telegramService;

    @Scheduled(fixedRate = 60000 * 10)
    public void fetchAndSendToTelegram() throws JsonProcessingException {
        final var price = flightService.searchFlights();
        log.info("Got price for given flight: {}", price);
        Optional.ofNullable(price).ifPresent(telegramService::sendMessage);
    }

}
