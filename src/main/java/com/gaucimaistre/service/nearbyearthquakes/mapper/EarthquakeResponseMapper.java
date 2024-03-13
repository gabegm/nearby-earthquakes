package com.gaucimaistre.service.nearbyearthquakes.mapper;

import org.springframework.stereotype.Component;

import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesByLocationResponse.EarthquakeResponse;
import com.gaucimaistre.service.nearbyearthquakes.model.Earthquake;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public abstract class EarthquakeResponseMapper {
    public static EarthquakeResponse mapToEarthquakeResponse(Earthquake earthquake) {
            log.info(earthquake.toString());

            return EarthquakeResponse.builder()
                .title(earthquake.getTitle())
                .build();
        }
}
