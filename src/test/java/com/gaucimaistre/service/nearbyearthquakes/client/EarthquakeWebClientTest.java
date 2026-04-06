package com.gaucimaistre.service.nearbyearthquakes.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesResponse;
import com.gaucimaistre.service.nearbyearthquakes.exception.EarthquakeRetrievalFailedException;

public class EarthquakeWebClientTest {
    private static final String URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson";
    private static final String FIXTURE = """
            {
              "features": [
                {
                  "id": "us6000mfqs",
                  "properties": { "place": "185 km NNW of Las Khorey, Somalia", "mag": 4.8, "time": 1700000000000 },
                  "geometry": { "coordinates": [49.1, 11.9, 10.0] }
                }
              ]
            }
            """;

    private EarthquakeWebClient client;
    private MockRestServiceServer server;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder();
        server = MockRestServiceServer.bindTo(builder).build();
        client = new EarthquakeWebClient(URL, builder.build());
    }

    @Test
    public void getEarthquakes_parsesResponseCorrectly() {
        server.expect(requestTo(URL))
                .andRespond(withSuccess(FIXTURE, MediaType.APPLICATION_JSON));

        GetEarthquakesResponse response = client.getEarthquakes();

        assertThat(response.features()).hasSize(1);
        GetEarthquakesResponse.Feature feature = response.features().get(0);
        assertThat(feature.id()).isEqualTo("us6000mfqs");
        assertThat(feature.properties().place()).isEqualTo("185 km NNW of Las Khorey, Somalia");
        assertThat(feature.properties().magnitude()).isEqualTo(4.8);
        assertThat(feature.geometry().coordinates()).containsExactly(49.1, 11.9, 10.0);

        server.verify();
    }

    @Test
    public void getEarthquakes_serverError_throwsEarthquakeRetrievalFailedException() {
        server.expect(requestTo(URL))
                .andRespond(withServerError());

        assertThatThrownBy(() -> client.getEarthquakes())
                .isInstanceOf(EarthquakeRetrievalFailedException.class);

        server.verify();
    }
}
