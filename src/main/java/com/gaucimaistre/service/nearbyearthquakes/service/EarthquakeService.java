package com.gaucimaistre.service.nearbyearthquakes.service;

import org.springframework.stereotype.Service;

import com.gaucimaistre.service.nearbyearthquakes.model.GetEarthquakesByLocationResponse;
import com.gaucimaistre.service.nearbyearthquakes.model.GetEarthquakesByLocationResponse.EarthquakeMapper;
import com.gaucimaistre.service.nearbyearthquakes.repository.EarthquakeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EarthquakeService {
    private final EarthquakeRepository repository;

    public GetEarthquakesByLocationResponse getNearbyEarthquakes(String latitude, String longitude) {
        return GetEarthquakesByLocationResponse.builder()
                .earthquakes(repository.findByDistance(Double.parseDouble(latitude), Double.parseDouble(longitude))
                    .stream()
                    .map(EarthquakeMapper::mapEarthquake)
                    .toList())
                .build();
    }
}
