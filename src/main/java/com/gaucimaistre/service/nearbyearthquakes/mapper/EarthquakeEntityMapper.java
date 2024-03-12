package com.gaucimaistre.service.nearbyearthquakes.mapper;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.gaucimaistre.service.nearbyearthquakes.model.EarthquakeEntity;
import com.gaucimaistre.service.nearbyearthquakes.model.GetEarthquakesResponse.Feature;

@Component
public abstract class EarthquakeEntityMapper {
    public static EarthquakeEntity mapToEarthquakeEntity(Feature feature) {
        return EarthquakeEntity.builder()
            .id(feature.getId())
            .place(feature.getProperties().getPlace())
            .magnitude(feature.getProperties().getMagnitude())
            .latitude(feature.getGeometry().getCoordinates().get(0))
            .longitude(feature.getGeometry().getCoordinates().get(1))
            .time(Instant.ofEpochSecond(feature.getProperties().getTime()))
            .build();
    }
}
