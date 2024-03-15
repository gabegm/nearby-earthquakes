package com.gaucimaistre.service.nearbyearthquakes.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gaucimaistre.service.nearbyearthquakes.client.EarthquakeClient;
import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesResponse;
import com.gaucimaistre.service.nearbyearthquakes.mapper.EarthquakeEntityMapper;
import com.gaucimaistre.service.nearbyearthquakes.model.EarthquakeEntity;
import com.gaucimaistre.service.nearbyearthquakes.repository.EarthquakeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("!test")
@RequiredArgsConstructor
public class RefreshEarthquakesTask {
    private final EarthquakeClient earthquakeWebClient;
    private final EarthquakeRepository repository;

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("HH:mm:ss")
            .withZone(ZoneId.systemDefault());
    private final EarthquakeEntityMapper earthquakeEntityMapper;

    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(cron = "0 9 * * * ?")
	public void refreshEarthquakes() {
		log.debug("The time is now {}", dateFormat.format(Instant.now()));

        GetEarthquakesResponse earthquakes = earthquakeWebClient.getEarthquakes();
        List<EarthquakeEntity> earthquakeEntities = earthquakes.features()
            .stream()
            .map(earthquakeEntityMapper::mapToEarthquakeEntity)
            .toList();

        repository.saveAll(earthquakeEntities);
	}
}
