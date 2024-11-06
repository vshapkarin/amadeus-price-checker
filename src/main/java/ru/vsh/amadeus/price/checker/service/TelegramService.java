package ru.vsh.amadeus.price.checker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class TelegramService {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.chat.id}")
    private String chatId;

    private final RestTemplate restTemplate;

    public void sendMessage(String message) {
        String url = String.format("https://api.telegram.org/bot%s/sendMessage", botToken);

        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonBody = String.format("{\"chat_id\": \"%s\", \"text\": \"%s\"}", chatId, message);

        final var request = new HttpEntity<>(jsonBody, headers);

        restTemplate.postForEntity(URI.create(url), request, String.class);
    }

}
