package com.gaucimaistre.service.nearbyearthquakes.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaucimaistre.service.nearbyearthquakes.model.Earthquake;
import com.gaucimaistre.service.nearbyearthquakes.model.EarthquakeEntity;

@Mapper
public interface EarthquakeMapper {
    @Mapping(target = "address", source = "place")
    Earthquake mapToEarthquake(EarthquakeEntity earthquakeEntity);
}