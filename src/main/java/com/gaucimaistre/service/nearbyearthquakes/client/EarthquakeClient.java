package com.gaucimaistre.service.nearbyearthquakes.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gaucimaistre.service.nearbyearthquakes.model.GetEarthquakesResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EarthquakeClient {
    @Value("${api.earthquakes.url}")
    private String URL;

    public GetEarthquakesResponse getEarthquakes() {
      log.info(URL);
      RestTemplate restTemplate = new RestTemplate();
      GetEarthquakesResponse earthquakes = restTemplate.getForObject(URL,
        GetEarthquakesResponse.class);

        return earthquakes;
    }
}
