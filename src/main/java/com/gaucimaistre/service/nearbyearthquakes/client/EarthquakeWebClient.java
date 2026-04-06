package com.gaucimaistre.service.nearbyearthquakes.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesResponse;
import com.gaucimaistre.service.nearbyearthquakes.exception.EarthquakeRetrievalFailedException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EarthquakeWebClient implements EarthquakeClient {
    private final String url;
    private final RestClient restClient;

    public EarthquakeWebClient(@Value("${api.earthquakes.url}") String url, RestClient restClient) {
        this.url = url;
        this.restClient = restClient;
    }

    @Override
    public GetEarthquakesResponse getEarthquakes() {
        log.debug("Fetching earthquakes from {}", url);
        try {
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(GetEarthquakesResponse.class);
        } catch (RestClientException exception) {
            final EarthquakeRetrievalFailedException earthquakeRetrievalFailedException = new EarthquakeRetrievalFailedException(
                    exception);
            log.error("API call failed", earthquakeRetrievalFailedException);
            throw earthquakeRetrievalFailedException;
        }
    }
}
