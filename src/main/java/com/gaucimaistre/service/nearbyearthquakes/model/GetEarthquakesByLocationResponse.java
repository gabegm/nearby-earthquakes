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
    private final List<EarthquakeMapper> earthquakes;

    @Builder
    @Getter
    @ToString
    public static class EarthquakeMapper {
        private final double magnitude;
        private final String address;
        private final int distance;

        public static EarthquakeMapper mapEarthquake(EarthquakeEntity earthquake) {
            log.info(earthquake.toString());
            return EarthquakeMapper.builder()
                .magnitude(earthquake.getMagnitude())
                .address(earthquake.getPlace())
                .distance(earthquake.getDistance())
                .build();
        }
    }
}
