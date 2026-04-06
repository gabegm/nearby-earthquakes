package com.gaucimaistre.service.nearbyearthquakes.mapper;

import com.gaucimaistre.service.nearbyearthquakes.model.Earthquake;
import com.gaucimaistre.service.nearbyearthquakes.model.EarthquakeEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-03T22:15:34+0100",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 25 (Oracle Corporation)"
)
@Component
public class EarthquakeMapperImpl implements EarthquakeMapper {

    @Override
    public Earthquake mapToEarthquake(EarthquakeEntity earthquakeEntity) {
        if ( earthquakeEntity == null ) {
            return null;
        }

        String address = null;
        double magnitude = 0.0d;
        int distance = 0;

        address = earthquakeEntity.getPlace();
        magnitude = earthquakeEntity.getMagnitude();
        distance = earthquakeEntity.getDistance();

        Earthquake earthquake = new Earthquake( magnitude, address, distance );

        return earthquake;
    }
}
