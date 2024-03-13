package com.gaucimaistre.service.nearbyearthquakes.client;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesResponse;
import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesResponse.Feature;
import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesResponse.Feature.Geometry;
import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesResponse.Feature.Properties;

@ExtendWith(MockitoExtension.class)
public class EarthquakeClinetTest {
    @Mock
    private EarthquakeClient client;

    @Test
    public void getEarthquakes() {
        GetEarthquakesResponse expectedResponse = GetEarthquakesResponse.builder()
            .features(List.of(Feature.builder()
                .id("someId")
                .properties(Properties.builder()
                    .place("somePlace")
                    .magnitude(1.5)
                    .time(1234L)
                    .build())
                .geometry(Geometry.builder()
                    .coordinates(List.of(1.1, 2.2, 3.3))
                    .build())
                .build(),
                Feature.builder()
                .id("someOtherId")
                .properties(Properties.builder()
                    .place("someOtherPlace")
                    .magnitude(2.1)
                    .time(5678L)
                    .build())
                .geometry(Geometry.builder()
                    .coordinates(List.of(4.4, 5.5, 6.6))
                    .build())
                .build()))
            .build();

        when(client.getEarthquakes()).thenReturn(expectedResponse);
        GetEarthquakesResponse actualResponse = client.getEarthquakes();

        assertThat(actualResponse.getFeatures().get(0).getId()).isEqualTo(expectedResponse.getFeatures().get(0).getId());
        assertThat(actualResponse.getFeatures().get(0).getProperties().getPlace()).isEqualTo(expectedResponse.getFeatures().get(0).getProperties().getPlace());
        assertThat(actualResponse.getFeatures().get(0).getProperties().getMagnitude()).isEqualTo(expectedResponse.getFeatures().get(0).getProperties().getMagnitude());
        assertThat(actualResponse.getFeatures().get(0).getProperties().getTime()).isEqualTo(expectedResponse.getFeatures().get(0).getProperties().getTime());
        assertThat(actualResponse.getFeatures().get(0).getGeometry().getCoordinates()).isEqualTo(expectedResponse.getFeatures().get(0).getGeometry().getCoordinates());

        assertThat(actualResponse.getFeatures().get(1).getId()).isEqualTo(expectedResponse.getFeatures().get(1).getId());
        assertThat(actualResponse.getFeatures().get(1).getProperties().getPlace()).isEqualTo(expectedResponse.getFeatures().get(1).getProperties().getPlace());
        assertThat(actualResponse.getFeatures().get(1).getProperties().getMagnitude()).isEqualTo(expectedResponse.getFeatures().get(1).getProperties().getMagnitude());
        assertThat(actualResponse.getFeatures().get(1).getProperties().getTime()).isEqualTo(expectedResponse.getFeatures().get(1).getProperties().getTime());
        assertThat(actualResponse.getFeatures().get(1).getGeometry().getCoordinates()).isEqualTo(expectedResponse.getFeatures().get(1).getGeometry().getCoordinates());
    }
}
