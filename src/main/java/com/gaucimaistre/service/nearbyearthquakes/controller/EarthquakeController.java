package com.gaucimaistre.service.nearbyearthquakes.controller;

import org.springframework.web.bind.annotation.RestController;

import com.gaucimaistre.service.nearbyearthquakes.model.GetEarthquakesByLocationResponse;
import com.gaucimaistre.service.nearbyearthquakes.service.EarthquakeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Slf4j
@RestController
@RequiredArgsConstructor
public class EarthquakeController {
    private final EarthquakeService service;

    @GetMapping("/nearby")
    @ResponseBody
    public GetEarthquakesByLocationResponse getNearbyEarthquakes(@RequestParam String latitude, @RequestParam String longitude) {
        return service.getNearbyEarthquakes(latitude, longitude);
    }
}
