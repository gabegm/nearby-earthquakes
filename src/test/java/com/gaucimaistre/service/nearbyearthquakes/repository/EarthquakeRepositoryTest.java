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
        List<EarthquakeEntity> earthquakes = List.of(
            new EarthquakeEntity("someId", 
                "somePlace", 
                1.1, 
                -122.7985001, 
                38.829834, 
                null, 
                0),
            new EarthquakeEntity("someOtherId", 
                "someOtherPlace", 
                2.2, 
                -155.1486, 
                57.5289, 
                null, 
                0)
        );

        repository.saveAll(earthquakes);

        List<EarthquakeEntity> actualEarthquakes = repository.findByMagnitudeGreaterThanEqual(1.0);
        assertThat(actualEarthquakes.size()).isEqualTo(2);
    }
}
