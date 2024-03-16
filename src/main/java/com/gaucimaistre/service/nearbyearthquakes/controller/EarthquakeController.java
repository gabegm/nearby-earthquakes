package com.gaucimaistre.service.nearbyearthquakes.controller;

import org.springframework.web.bind.annotation.RestController;

import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesByLocationResponse;
import com.gaucimaistre.service.nearbyearthquakes.service.EarthquakeService;

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

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String home() {
        log.info("Handling home request");
        return "";
    }

    @ResponseBody
    @GetMapping(value = "/nearby", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetEarthquakesByLocationResponse getNearbyEarthquakes(@RequestParam Double latitude,
            @RequestParam Double longitude) {
        log.info("Handling GetNearbyEarthquakes request");
        GetEarthquakesByLocationResponse response = service.getNearbyEarthquakes(new Point(latitude, longitude));
        log.debug(response.toString());

        return response;
    }
}
