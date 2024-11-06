package ru.vsh.amadeus.price.checker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.vsh.amadeus.price.checker.model.AmadeusAuthResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AmadeusAuthService {

    @Value("${amadeus.api.client_id}")
    private String clientId;

    @Value("${amadeus.api.client_secret}")
    private String clientSecret;

    @Value("${amadeus.api.token_url}")
    private String tokenUrl;

    private final RestTemplate restTemplate;

    public String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

        ResponseEntity<AmadeusAuthResponse> response = restTemplate.exchange(
                tokenUrl,
                HttpMethod.POST,
                requestEntity,
                AmadeusAuthResponse.class
        );

        return Optional.ofNullable(response.getBody())
                .map(AmadeusAuthResponse::getAccessToken)
                .orElseThrow(() -> new RuntimeException("Amadeus Auth service returned empty body"));
    }
}
