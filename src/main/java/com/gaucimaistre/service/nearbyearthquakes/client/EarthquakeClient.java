package com.gaucimaistre.service.nearbyearthquakes.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EarthquakeClient {
    @Value("${api.earthquakes.url}")
    private String URL;
    private final WebClient webClient;

    public GetEarthquakesResponse getEarthquakes() {
      log.info(URL);

      GetEarthquakesResponse earthquakes = webClient.get()
              .uri(URL)
              .retrieve()
              .bodyToMono(GetEarthquakesResponse.class)
              .block();

        return earthquakes;
    }
}
