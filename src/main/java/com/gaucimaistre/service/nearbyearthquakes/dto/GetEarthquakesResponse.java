package com.gaucimaistre.service.nearbyearthquakes.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetEarthquakesResponse(List<Feature> features) {

    public record Feature(String id, Properties properties, Geometry geometry) {
        public record Properties(String place, @JsonProperty("mag") double magnitude, Long time) {}
        public record Geometry(List<Double> coordinates) {}
    }
}