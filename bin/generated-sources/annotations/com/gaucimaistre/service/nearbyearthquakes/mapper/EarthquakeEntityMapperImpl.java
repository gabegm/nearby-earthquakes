package com.gaucimaistre.service.nearbyearthquakes.mapper;

import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesResponse;
import com.gaucimaistre.service.nearbyearthquakes.model.EarthquakeEntity;
import java.time.Instant;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-03T22:15:34+0100",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 25 (Oracle Corporation)"
)
@Component
public class EarthquakeEntityMapperImpl implements EarthquakeEntityMapper {

    @Override
    public EarthquakeEntity mapToEarthquakeEntity(GetEarthquakesResponse.Feature feature) {
        if ( feature == null ) {
            return null;
        }

        String place = null;
        double magnitude = 0.0d;
        String id = null;

        place = featurePropertiesPlace( feature );
        magnitude = featurePropertiesMagnitude( feature );
        id = feature.id();

        double latitude = feature.geometry().coordinates().get(0);
        double longitude = feature.geometry().coordinates().get(1);
        Instant time = Instant.ofEpochSecond(feature.properties().time());
        int distance = 0;

        EarthquakeEntity earthquakeEntity = new EarthquakeEntity( id, place, magnitude, latitude, longitude, time, distance );

        return earthquakeEntity;
    }

    private String featurePropertiesPlace(GetEarthquakesResponse.Feature feature) {
        GetEarthquakesResponse.Feature.Properties properties = feature.properties();
        if ( properties == null ) {
            return null;
        }
        return properties.place();
    }

    private double featurePropertiesMagnitude(GetEarthquakesResponse.Feature feature) {
        GetEarthquakesResponse.Feature.Properties properties = feature.properties();
        if ( properties == null ) {
            return 0.0d;
        }
        return properties.magnitude();
    }
}
