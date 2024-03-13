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
        GetEarthquakesByLocationResponse expectedResponse = GetEarthquakesByLocationResponse.builder()
            .earthquakes(List.of(EarthquakeResponse.builder()
                .title("someTitle")
                .build(),
                EarthquakeResponse.builder()
                .title("someOtherTitle")
                .build()))
            .build();

        when(service.getNearbyEarthquakes("1.1", "2.2")).thenReturn(expectedResponse);
        GetEarthquakesByLocationResponse actualResponse = service.getNearbyEarthquakes("1.1", "2.2");

        assertThat(actualResponse.getEarthquakes().get(0).getTitle()).isEqualTo(expectedResponse.getEarthquakes().get(0).getTitle());
        assertThat(actualResponse.getEarthquakes().get(1).getTitle()).isEqualTo(expectedResponse.getEarthquakes().get(1).getTitle());
    }
}
