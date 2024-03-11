package com.gaucimaistre.service.nearbyearthquakes.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.gaucimaistre.service.nearbyearthquakes.NearbyEarthquakesApplication;
import com.gaucimaistre.service.nearbyearthquakes.model.EarthquakeEntity;

@SpringBootTest(classes = NearbyEarthquakesApplication.class)
@ActiveProfiles("test")
@AutoConfigureDataJpa
public class EarthquakeRepositoryTest {
    @Autowired
    private EarthquakeRepository repository;

    @Test
    public void findByMagnitudeGreaterThanEqual() {
        List<EarthquakeEntity> earthquakes = List.of(EarthquakeEntity.builder()
                .id("someId")
                .place("somePlace")
                .magnitude(1.1)
                .latitude(-122.7985001)
                .longitude(38.829834)
                .build(),
            EarthquakeEntity.builder()
                .id("someOtherId")
                .place("someOtherPlace")
                .magnitude(2.2)
                .latitude(-155.1486)
                .longitude(57.5289)
                .build());

        repository.saveAll(earthquakes);

        List<EarthquakeEntity> actualEarthquakes = repository.findByMagnitudeGreaterThanEqual(1.0);
        assertThat(actualEarthquakes.size()).isEqualTo(2);
    }
}
