package com.gaucimaistre.service.nearbyearthquakes.client;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesResponse;
import com.gaucimaistre.service.nearbyearthquakes.exception.EarthquakeRetrievalFailedException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EarthquakeFileClient implements EarthquakeClient {
    private final ObjectMapper mapper;

    @Override
    public GetEarthquakesResponse getEarthquakes() {
        try {
            File file = ResourceUtils.getFile("classpath:static/all_month.geojson");

            GetEarthquakesResponse earthquakes = mapper.readValue(file, GetEarthquakesResponse.class);

            return earthquakes;
        } catch (IOException exception) {
            final EarthquakeRetrievalFailedException earthquakeRetrievalFailedException = new EarthquakeRetrievalFailedException(exception);
            log.error("File parsing failed", earthquakeRetrievalFailedException);
            throw earthquakeRetrievalFailedException;
        }
    }
}
