package com.gaucimaistre.service.nearbyearthquakes.dto;

import java.util.List;

public record GetEarthquakesByLocationResponse(List<EarthquakeResponse> earthquakes) {

    public record EarthquakeResponse(String title) {
    }
}