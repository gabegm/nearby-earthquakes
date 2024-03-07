package com.gaucimaistre.service.nearbyearthquakes.client;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gaucimaistre.service.nearbyearthquakes.model.GetEarthquakesResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EarthquakeClient {
    private final String url = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson";

    public GetEarthquakesResponse getEarthquakes() {
      RestTemplate restTemplate = new RestTemplate();
      GetEarthquakesResponse earthquakes = restTemplate.getForObject(url,
        GetEarthquakesResponse.class);

        return earthquakes;
    }
}
