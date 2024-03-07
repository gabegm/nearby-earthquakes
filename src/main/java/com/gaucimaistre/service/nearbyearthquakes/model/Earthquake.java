package com.gaucimaistre.service.nearbyearthquakes.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Earthquake {
    private final double magnitude;
    private final String address;
    private final int distance;

    private static Earthquake getEarthquake(GetEarthquakesResponse earthquake) {
        return Earthquake.builder()
            .build();
    }

    private static Earthquake getEarthquake(EarthquakeEntity earthquake) {
        return Earthquake.builder()
            .build();
    }
}
