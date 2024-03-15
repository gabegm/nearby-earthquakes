package com.gaucimaistre.service.nearbyearthquakes.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesByLocationResponse;
import com.gaucimaistre.service.nearbyearthquakes.exception.EarthquakeRetrievalFailedException;
import com.gaucimaistre.service.nearbyearthquakes.service.EarthquakeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
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

    @GetMapping(value = "/nearby", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GetEarthquakesByLocationResponse getNearbyEarthquakes(@RequestParam String latitude, @RequestParam String longitude) {
        try {
            log.info("Handling GetNearbyEarthquakes request");
            GetEarthquakesByLocationResponse response = service.getNearbyEarthquakes(latitude, longitude);
            log.debug(response.toString());

            return response;
        } catch (EarthquakeRetrievalFailedException exception) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Unable to fetch data from downstream API");
        } catch (Exception exception) {
            log.error("GetNearbyEarthquakes request failed", exception);
        }

        return new GetEarthquakesByLocationResponse(null);
    }
}
