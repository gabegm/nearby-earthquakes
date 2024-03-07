package com.gaucimaistre.service.nearbyearthquakes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NearbyEarthquakesApplication {

	public static void main(String[] args) {
		SpringApplication.run(NearbyEarthquakesApplication.class, args);
	}

}
