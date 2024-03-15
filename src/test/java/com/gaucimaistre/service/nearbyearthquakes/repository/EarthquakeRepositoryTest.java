package com.gaucimaistre.service.nearbyearthquakes.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.gaucimaistre.service.nearbyearthquakes.NearbyEarthquakesApplication;
import com.gaucimaistre.service.nearbyearthquakes.client.EarthquakeClient;
import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesResponse;
import com.gaucimaistre.service.nearbyearthquakes.mapper.EarthquakeEntityMapper;
import com.gaucimaistre.service.nearbyearthquakes.model.EarthquakeEntity;

@SpringBootTest(classes = NearbyEarthquakesApplication.class)
@ActiveProfiles("test")
@AutoConfigureDataJpa
public class EarthquakeRepositoryTest {
    @Autowired
    private EarthquakeRepository repository;
    @Autowired
    private EarthquakeClient earthquakeFileClient;
    @Autowired
    private EarthquakeEntityMapper earthquakeEntityMapper;

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

    @Test
    public void findByDistance() {
        GetEarthquakesResponse earthquakes = earthquakeFileClient.getEarthquakes();
        List<EarthquakeEntity> earthquakeEntities = earthquakes.features()
            .stream()
            .map(earthquakeEntityMapper::mapToEarthquakeEntity)
            .toList();

        repository.saveAll(earthquakeEntities);

        List<EarthquakeEntity> actualEarthquakes = repository.findByDistance(40.730610, -73.935242);
        assertThat(actualEarthquakes.size()).isEqualTo(10);

        assertThat(actualEarthquakes.get(0).getId()).isEqualTo("us6000mfqs");
        assertThat(actualEarthquakes.get(0).getDistance()).isEqualTo(2709);

        assertThat(actualEarthquakes.get(1).getId()).isEqualTo("us7000m12h");
        assertThat(actualEarthquakes.get(1).getDistance()).isEqualTo(3247);
    }
}