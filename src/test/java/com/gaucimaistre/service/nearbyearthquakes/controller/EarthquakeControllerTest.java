package com.gaucimaistre.service.nearbyearthquakes.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesByLocationResponse;
import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesByLocationResponse.EarthquakeResponse;
import com.gaucimaistre.service.nearbyearthquakes.service.EarthquakeService;

@ExtendWith(MockitoExtension.class)
public class EarthquakeControllerTest {
    @Mock
    private EarthquakeService service;

    @Test
    public void getNearbyEarthquakes() {
        GetEarthquakesByLocationResponse expectedResponse = new GetEarthquakesByLocationResponse(
            List.of(
                new EarthquakeResponse("someTitle"),
                new EarthquakeResponse("someOtherTitle")
            )
        );

        when(service.getNearbyEarthquakes("1.1", "2.2")).thenReturn(expectedResponse);
        GetEarthquakesByLocationResponse actualResponse = service.getNearbyEarthquakes("1.1", "2.2");

        assertThat(actualResponse.earthquakes().get(0).title()).isEqualTo(expectedResponse.earthquakes().get(0).title());
        assertThat(actualResponse.earthquakes().get(1).title()).isEqualTo(expectedResponse.earthquakes().get(1).title());
    }
}
