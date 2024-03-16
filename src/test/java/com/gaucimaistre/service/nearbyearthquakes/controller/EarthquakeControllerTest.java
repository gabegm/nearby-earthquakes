package com.gaucimaistre.service.nearbyearthquakes.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.geo.Point;

import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesByLocationResponse;
import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesByLocationResponse.EarthquakeResponse;
import com.gaucimaistre.service.nearbyearthquakes.exception.EarthquakeRetrievalFailedException;
import com.gaucimaistre.service.nearbyearthquakes.service.EarthquakeService;

@ExtendWith(MockitoExtension.class)
public class EarthquakeControllerTest {
        @Mock
        private EarthquakeService service;

        @InjectMocks
        private EarthquakeController controller;

        @Test
        public void getNearbyEarthquakes() {
                GetEarthquakesByLocationResponse expectedResponse = new GetEarthquakesByLocationResponse(
                                List.of(
                                                new EarthquakeResponse("someTitle"),
                                                new EarthquakeResponse("someOtherTitle")));

                Point point = new Point(1.1, 2.2);
                when(service.getNearbyEarthquakes(point)).thenReturn(expectedResponse);
                GetEarthquakesByLocationResponse actualResponse = service.getNearbyEarthquakes(point);

                assertThat(actualResponse.earthquakes().get(0).title())
                                .isEqualTo(expectedResponse.earthquakes().get(0).title());
                assertThat(actualResponse.earthquakes().get(1).title())
                                .isEqualTo(expectedResponse.earthquakes().get(1).title());
        }

        @Test
        public void getNearbyEarthquakesException() {
                Point point = new Point(1.1, 2.2);
                when(service.getNearbyEarthquakes(point))
                                .thenThrow(new EarthquakeRetrievalFailedException(new RuntimeException()));

                assertThatThrownBy(() -> controller.getNearbyEarthquakes(point.getX(), point.getY()))
                                .isInstanceOf(EarthquakeRetrievalFailedException.class);
        }
}
