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
public class EarthquakeWebClientTest {
    @Mock
    private EarthquakeClient earthquakeWebClient;

    @Test
    public void getEarthquakes() {
        GetEarthquakesResponse expectedResponse = new GetEarthquakesResponse(
            List.of(
                new Feature(
                    "someId",
                    new Properties("somePlace", 1.5, 1234L),
                    new Geometry(List.of(1.1, 2.2, 3.3))
                ),
                new Feature(
                    "someOtherId",
                    new Properties("someOtherPlace", 2.1, 5678L),
                    new Geometry(List.of(4.4, 5.5, 6.6))
                )
            )
        );

        when(earthquakeWebClient.getEarthquakes()).thenReturn(expectedResponse);
        GetEarthquakesResponse actualResponse = earthquakeWebClient.getEarthquakes();

        assertThat(actualResponse.features().get(0).id()).isEqualTo(expectedResponse.features().get(0).id());
        assertThat(actualResponse.features().get(0).properties().place()).isEqualTo(expectedResponse.features().get(0).properties().place());
        assertThat(actualResponse.features().get(0).properties().magnitude()).isEqualTo(expectedResponse.features().get(0).properties().magnitude());
        assertThat(actualResponse.features().get(0).properties().time()).isEqualTo(expectedResponse.features().get(0).properties().time());
        assertThat(actualResponse.features().get(0).geometry().coordinates()).isEqualTo(expectedResponse.features().get(0).geometry().coordinates());

        assertThat(actualResponse.features().get(1).id()).isEqualTo(expectedResponse.features().get(1).id());
        assertThat(actualResponse.features().get(1).properties().place()).isEqualTo(expectedResponse.features().get(1).properties().place());
        assertThat(actualResponse.features().get(1).properties().magnitude()).isEqualTo(expectedResponse.features().get(1).properties().magnitude());
        assertThat(actualResponse.features().get(1).properties().time()).isEqualTo(expectedResponse.features().get(1).properties().time());
        assertThat(actualResponse.features().get(1).geometry().coordinates()).isEqualTo(expectedResponse.features().get(1).geometry().coordinates());
    }
}
