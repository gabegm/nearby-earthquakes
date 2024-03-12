package com.gaucimaistre.service.nearbyearthquakes.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class GetEarthquakesByLocationResponse {
    private final List<EarthquakeResponse> earthquakes;

    @Builder
    @Getter
    @ToString
    public static class EarthquakeResponse {
        private final String title;
    }
}
