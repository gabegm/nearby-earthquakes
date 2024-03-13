package com.gaucimaistre.service.nearbyearthquakes.service;

import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesByLocationResponse;
import com.gaucimaistre.service.nearbyearthquakes.mapper.EarthquakeMapper;
import com.gaucimaistre.service.nearbyearthquakes.mapper.EarthquakeResponseMapper;
import com.gaucimaistre.service.nearbyearthquakes.model.Earthquake;
import com.gaucimaistre.service.nearbyearthquakes.repository.EarthquakeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EarthquakeService {
    private final EarthquakeRepository repository;

    private static final EarthquakeMapper earthquakeMapper = Mappers.getMapper(EarthquakeMapper.class);
    private static final EarthquakeResponseMapper earthquakeResponseMapper = Mappers.getMapper(EarthquakeResponseMapper.class);

    public GetEarthquakesByLocationResponse getNearbyEarthquakes(String latitude, String longitude) {
        List<Earthquake> earthquakes = repository.findByDistance(Double.parseDouble(latitude), Double.parseDouble(longitude))
            .stream()
            .map(earthquakeMapper::mapToEarthquake)
            .toList();

        return new GetEarthquakesByLocationResponse(earthquakes.stream()
                .map(earthquakeResponseMapper::mapToEarthquakeResponse)
                .toList());
    }
}