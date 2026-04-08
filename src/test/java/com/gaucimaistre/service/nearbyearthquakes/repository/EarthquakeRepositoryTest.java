package com.gaucimaistre.service.nearbyearthquakes.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.gaucimaistre.service.nearbyearthquakes.model.EarthquakeEntity;

@DataJpaTest
public class EarthquakeRepositoryTest {
    @Autowired
    private EarthquakeRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    private EarthquakeEntity recentEarthquake(String id, String place, double magnitude, double longitude, double latitude) {
        return new EarthquakeEntity(id, place, magnitude, latitude, longitude, Instant.now(), 0);
    }

    @Test
    public void findByMagnitudeGreaterThanEqual_returnsMatchingEarthquakes() {
        repository.saveAll(List.of(
                recentEarthquake("id1", "Place A", 1.1, -122.7, 38.8),
                recentEarthquake("id2", "Place B", 2.2, -155.1, 57.5),
                recentEarthquake("id3", "Place C", 0.5, -100.0, 30.0)));

        List<EarthquakeEntity> result = repository.findByMagnitudeGreaterThanEqual(1.0);
        assertThat(result).hasSize(2);
    }

    @Test
    public void findByMagnitudeLessThanEqual_returnsMatchingEarthquakes() {
        repository.saveAll(List.of(
                recentEarthquake("id1", "Place A", 1.1, -122.7, 38.8),
                recentEarthquake("id2", "Place B", 3.5, -155.1, 57.5)));

        List<EarthquakeEntity> result = repository.findByMagnitudeLessThanEqual(2.0);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo("id1");
    }

    @Test
    public void findByMagnitudeBetween_returnsMatchingEarthquakes() {
        repository.saveAll(List.of(
                recentEarthquake("id1", "Place A", 1.0, -122.7, 38.8),
                recentEarthquake("id2", "Place B", 2.5, -155.1, 57.5),
                recentEarthquake("id3", "Place C", 5.0, -100.0, 30.0)));

        List<EarthquakeEntity> result = repository.findByMagnitudeBetween(1.5, 3.0);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo("id2");
    }

    @Test
    public void findByDistance_returnsAtMost10Results_orderedByAscendingDistance() {
        // Seed 15 earthquakes near New York (40.73, -73.94) at varying distances
        List<EarthquakeEntity> seed = List.of(
                recentEarthquake("eq1",  "Very close A",  4.0, -73.94, 40.73),
                recentEarthquake("eq2",  "Very close B",  3.5, -73.95, 40.74),
                recentEarthquake("eq3",  "Near A",        2.1, -74.50, 41.00),
                recentEarthquake("eq4",  "Near B",        1.8, -74.00, 41.50),
                recentEarthquake("eq5",  "Mid A",         3.0, -75.00, 42.00),
                recentEarthquake("eq6",  "Mid B",         2.5, -72.00, 39.00),
                recentEarthquake("eq7",  "Mid C",         4.2, -71.00, 43.00),
                recentEarthquake("eq8",  "Far A",         5.0, -70.00, 44.00),
                recentEarthquake("eq9",  "Far B",         3.3, -69.00, 45.00),
                recentEarthquake("eq10", "Far C",         2.8, -68.00, 46.00),
                recentEarthquake("eq11", "Far D",         1.5, -67.00, 47.00),
                recentEarthquake("eq12", "Far E",         2.0, -66.00, 48.00),
                recentEarthquake("eq13", "Far F",         3.1, -65.00, 49.00),
                recentEarthquake("eq14", "Far G",         4.0, -64.00, 50.00),
                recentEarthquake("eq15", "Far H",         2.2, -63.00, 51.00));
        repository.saveAll(seed);

        List<EarthquakeEntity> result = repository.findByDistance(40.73, -73.94);

        assertThat(result).hasSize(10);
        // Verify ordering: each earthquake should be no closer than the previous
        for (int i = 1; i < result.size(); i++) {
            assertThat(result.get(i).getDistance()).isGreaterThanOrEqualTo(result.get(i - 1).getDistance());
        }
        // The two closest should be the ones seeded right at the query point
        assertThat(result.get(0).getId()).isIn("eq1", "eq2");
        assertThat(result.get(1).getId()).isIn("eq1", "eq2");
    }
}
