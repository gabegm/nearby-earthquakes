package com.gaucimaistre.service.nearbyearthquakes.mapper;

import java.time.Instant;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesResponse.Feature;
import com.gaucimaistre.service.nearbyearthquakes.model.EarthquakeEntity;

@Mapper(componentModel = "spring", imports = Instant.class)
public interface EarthquakeEntityMapper {
    @Mapping(target = "place", source = "feature.properties.place")
    @Mapping(target = "magnitude", source = "feature.properties.magnitude")
    @Mapping(target = "latitude", expression = "java(feature.geometry().coordinates().get(0))")
    @Mapping(target = "longitude", expression = "java(feature.geometry().coordinates().get(1))")
    @Mapping(target = "time", expression = "java(Instant.ofEpochSecond(feature.properties().time()))")
    @Mapping(target = "distance", ignore = true)
    EarthquakeEntity mapToEarthquakeEntity(Feature feature);
}