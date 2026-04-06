package com.gaucimaistre.service.nearbyearthquakes.mapper;

import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesByLocationResponse;
import com.gaucimaistre.service.nearbyearthquakes.model.Earthquake;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-03T22:15:34+0100",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 25 (Oracle Corporation)"
)
@Component
public class EarthquakeResponseMapperImpl implements EarthquakeResponseMapper {

    @Override
    public GetEarthquakesByLocationResponse.EarthquakeResponse mapToEarthquakeResponse(Earthquake earthquake) {
        if ( earthquake == null ) {
            return null;
        }

        String title = earthquake.getTitle();

        GetEarthquakesByLocationResponse.EarthquakeResponse earthquakeResponse = new GetEarthquakesByLocationResponse.EarthquakeResponse( title );

        return earthquakeResponse;
    }
}
