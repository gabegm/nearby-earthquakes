package com.gaucimaistre.service.nearbyearthquakes.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

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
}
