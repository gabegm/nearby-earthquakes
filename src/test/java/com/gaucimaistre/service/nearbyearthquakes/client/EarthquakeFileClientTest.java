package com.gaucimaistre.service.nearbyearthquakes.client;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesResponse;

@SpringBootTest
@ActiveProfiles("test")
public class EarthquakeFileClientTest {
    @Autowired
    private EarthquakeFileClient earthquakeFileClient;

    @Test
    public void getEarthquakes_parsesFileAndReturnsFeatures() {
        GetEarthquakesResponse response = earthquakeFileClient.getEarthquakes();

        assertThat(response).isNotNull();
        assertThat(response.features()).isNotEmpty();

        GetEarthquakesResponse.Feature first = response.features().get(0);
        assertThat(first.id()).isNotBlank();
        assertThat(first.properties()).isNotNull();
        assertThat(first.properties().place()).isNotBlank();
        assertThat(first.geometry()).isNotNull();
        assertThat(first.geometry().coordinates()).hasSize(3);
    }
}
