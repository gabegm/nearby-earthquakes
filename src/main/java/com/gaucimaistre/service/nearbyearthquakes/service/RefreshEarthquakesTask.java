package com.gaucimaistre.service.nearbyearthquakes.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gaucimaistre.service.nearbyearthquakes.client.EarthquakeClient;
import com.gaucimaistre.service.nearbyearthquakes.model.EarthquakeEntity;
import com.gaucimaistre.service.nearbyearthquakes.model.GetEarthquakesResponse;
import com.gaucimaistre.service.nearbyearthquakes.repository.EarthquakeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshEarthquakesTask {
    private final EarthquakeClient client;
    private final EarthquakeRepository repository;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000 * 60)
	public void refreshEarthquakes() {
		log.info("The time is now {}", dateFormat.format(new Date()));
        GetEarthquakesResponse earthquakes = client.getEarthquakes();
        List<EarthquakeEntity> earthquakeEntities = earthquakes.getFeatures()
            .stream()
            .map(GetEarthquakesResponse::getEarthquake)
            .toList();

        repository.saveAll(earthquakeEntities);
	}
}
