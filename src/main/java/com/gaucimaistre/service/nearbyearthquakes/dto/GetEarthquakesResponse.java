package com.gaucimaistre.service.nearbyearthquakes.dto;

import java.time.Instant;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gaucimaistre.service.nearbyearthquakes.model.EarthquakeEntity;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetEarthquakesResponse {
    private List<Feature> features;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Feature {
        private String id;
        private Properties properties;
        private Geometry geometry;

        @NoArgsConstructor
        @Builder
        @AllArgsConstructor
        @Getter
        public static class Properties {
            private String place;
            @JsonProperty("mag")
            private double magnitude;
            private Long time;
        }

        @NoArgsConstructor
        @Builder
        @AllArgsConstructor
        @Getter
        public static class Geometry {
            private List<Double> coordinates;
        }
    }

    public static EarthquakeEntity getEarthquake(Feature feature) {
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
