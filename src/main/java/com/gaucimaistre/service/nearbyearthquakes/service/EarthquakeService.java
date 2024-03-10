package com.gaucimaistre.service.nearbyearthquakes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gaucimaistre.service.nearbyearthquakes.model.Earthquake;
import com.gaucimaistre.service.nearbyearthquakes.model.EarthquakeEntity;
import com.gaucimaistre.service.nearbyearthquakes.model.GetEarthquakesByLocationResponse;
import com.gaucimaistre.service.nearbyearthquakes.model.GetEarthquakesByLocationResponse.EarthquakeResponse;
import com.gaucimaistre.service.nearbyearthquakes.repository.EarthquakeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EarthquakeService {
    private final EarthquakeRepository repository;

    public GetEarthquakesByLocationResponse getNearbyEarthquakes(String latitude, String longitude) {
        List<Earthquake> earthquakes = repository.findByDistance(Double.parseDouble(latitude), Double.parseDouble(longitude))
            .stream()
            .map(EarthquakeEntity::getEarthquake)
            .toList();

        return GetEarthquakesByLocationResponse.builder()
                .earthquakes(earthquakes
                    .stream()
                    .map(EarthquakeResponse::getEarthquake)
                    .toList())
                .build();
    }
}
