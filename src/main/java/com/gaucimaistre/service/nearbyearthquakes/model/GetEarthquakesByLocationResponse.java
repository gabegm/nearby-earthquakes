package com.gaucimaistre.service.nearbyearthquakes.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Builder
@Getter
@ToString
@Slf4j
public class GetEarthquakesByLocationResponse {
    private final List<EarthquakeResponse> earthquakes;

    @Builder
    @Getter
    @ToString
    public static class EarthquakeResponse {
        private final String title;

        public static EarthquakeResponse getEarthquake(Earthquake earthquake) {
            log.info(earthquake.toString());
            return EarthquakeResponse.builder()
                .title(earthquake.getTitle())
                .build();
        }
    }
}
