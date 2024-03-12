package com.gaucimaistre.service.nearbyearthquakes.mapper;

import org.springframework.stereotype.Component;

import com.gaucimaistre.service.nearbyearthquakes.model.Earthquake;
import com.gaucimaistre.service.nearbyearthquakes.model.EarthquakeEntity;

@Component
public abstract class EarthquakeMapper {

    public static Earthquake mapToEarthquake(EarthquakeEntity earthquakeEntity) {
        return Earthquake.builder()
            .magnitude(earthquakeEntity.getMagnitude())
            .address(earthquakeEntity.getPlace())
            .distance(earthquakeEntity.getDistance())
            .build();
    }
}