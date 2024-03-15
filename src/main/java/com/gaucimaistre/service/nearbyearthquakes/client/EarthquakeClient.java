package com.gaucimaistre.service.nearbyearthquakes.client;

import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesResponse;

public interface EarthquakeClient {
    public GetEarthquakesResponse getEarthquakes();
}