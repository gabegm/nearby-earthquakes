package com.gaucimaistre.service.nearbyearthquakes.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.geo.Point;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesByLocationResponse;
import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesByLocationResponse.EarthquakeResponse;
import com.gaucimaistre.service.nearbyearthquakes.exception.EarthquakeRetrievalFailedException;
import com.gaucimaistre.service.nearbyearthquakes.service.EarthquakeService;

@WebMvcTest(EarthquakeController.class)
public class EarthquakeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EarthquakeService service;

    @Test
    public void getNearbyEarthquakes() throws Exception {
        GetEarthquakesByLocationResponse expectedResponse = new GetEarthquakesByLocationResponse(
                List.of(
                        new EarthquakeResponse("someTitle"),
                        new EarthquakeResponse("someOtherTitle")));

        when(service.getNearbyEarthquakes(any(Point.class))).thenReturn(expectedResponse);

        mockMvc.perform(get("/nearby")
                        .param("latitude", "48.193889")
                        .param("longitude", "11.221226")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.earthquakes[0].title").value("someTitle"))
                .andExpect(jsonPath("$.earthquakes[1].title").value("someOtherTitle"));
    }

    @Test
    public void getNearbyEarthquakes_serviceFailure_returns424() throws Exception {
        when(service.getNearbyEarthquakes(any(Point.class)))
                .thenThrow(new EarthquakeRetrievalFailedException(new org.springframework.web.client.RestClientException("timeout")));

        mockMvc.perform(get("/nearby")
                        .param("latitude", "48.193889")
                        .param("longitude", "11.221226")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFailedDependency());
    }

    @Test
    public void getNearbyEarthquakes_invalidLatitude_returns400() throws Exception {
        mockMvc.perform(get("/nearby")
                        .param("latitude", "999.0")
                        .param("longitude", "11.221226")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
