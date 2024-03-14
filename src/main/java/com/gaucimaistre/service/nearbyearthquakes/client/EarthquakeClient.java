package com.gaucimaistre.service.nearbyearthquakes.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesResponse;
import com.gaucimaistre.service.nearbyearthquakes.exception.EarthquakeRetrievalFailedException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EarthquakeClient {
    @Value("${api.earthquakes.url}")
    private String URL;
    private final WebClient webClient;

    public GetEarthquakesResponse getEarthquakes() throws EarthquakeRetrievalFailedException {
        log.info(URL);
        try {
        GetEarthquakesResponse earthquakes = webClient.get()
                .uri(URL)
                .retrieve()
                .bodyToMono(GetEarthquakesResponse.class)
                .block();

        return earthquakes;
        } catch (RuntimeException exception) {
            final EarthquakeRetrievalFailedException earthquakeRetrievalFailedException = new EarthquakeRetrievalFailedException(exception);
            log.error("API call failed", earthquakeRetrievalFailedException);
            throw earthquakeRetrievalFailedException;
        }
    }
}

/*
public void sendNotification(String notification) throws NotificationException {
    final WebClient webClient = WebClient.builder()
        .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .build();
    webClient.post()
        .uri("http://localhost:9000/api")
        .body(BodyInserters.fromValue(notification))
        .retrieve()
        .onStatus(HttpStatus::isError, clientResponse -> Mono.error(NotificationException::new))
        .toBodilessEntity()
        .block();
    log.info("Notification delivered successfully");
}
 */
