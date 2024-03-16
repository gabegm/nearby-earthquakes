package com.gaucimaistre.service.nearbyearthquakes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gaucimaistre.service.nearbyearthquakes.model.EarthquakeEntity;

public interface EarthquakeRepository extends JpaRepository<EarthquakeEntity, String> {
    @Query(value = """
            SELECT
                ROUND(111.111 *
                DEGREES(ACOS(LEAST(1.0, COS(RADIANS(latitude))
                    * COS(RADIANS(:latitude))
                    * COS(RADIANS(longitude - :longitude))
                    + SIN(RADIANS(latitude))
                    * SIN(RADIANS(:latitude))))), 0) AS distance
                    , id
                    , place
                    , latitude
                    , longitude
                    , magnitude
                    , time
            FROM (
                SELECT
                    id
                    , place
                    , latitude
                    , longitude
                    , magnitude
                    , time
                    , ROW_NUMBER() OVER(PARTITION BY latitude, longitude ORDER BY id) AS rn
                FROM earthquake
            ) AS earthquake
            WHERE time >= DATEADD('DAY', -30, CURRENT_DATE)
            AND rn = 1
            ORDER BY
                1 ASC
            LIMIT 10
            """, nativeQuery = true)
    List<EarthquakeEntity> findByDistance(@Param("latitude") double latitude,
            @Param("longitude") double longitude);

    List<EarthquakeEntity> findByMagnitudeGreaterThanEqual(double magnitude);

    List<EarthquakeEntity> findByMagnitudeLessThanEqual(double magnitude);

    List<EarthquakeEntity> findByMagnitudeBetween(double from, double to);
}