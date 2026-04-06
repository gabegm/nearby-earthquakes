package com.gaucimaistre.service.nearbyearthquakes.controller;

import org.springframework.web.bind.annotation.RestController;

import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesByLocationResponse;
import com.gaucimaistre.service.nearbyearthquakes.service.EarthquakeService;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.geo.Point;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class EarthquakeController {
    private final EarthquakeService service;

    @ResponseBody
    @GetMapping(value = "/nearby", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetEarthquakesByLocationResponse getNearbyEarthquakes(
            @RequestParam @NotNull @DecimalMin("-90.0") @DecimalMax("90.0") Double latitude,
            @RequestParam @NotNull @DecimalMin("-180.0") @DecimalMax("180.0") Double longitude) {
        log.info("Handling GetNearbyEarthquakes request");
        GetEarthquakesByLocationResponse response = service.getNearbyEarthquakes(new Point(latitude, longitude));
        log.debug(response.toString());

        return response;
    }
}
