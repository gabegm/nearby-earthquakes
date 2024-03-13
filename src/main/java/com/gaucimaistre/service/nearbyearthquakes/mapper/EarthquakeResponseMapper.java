package com.gaucimaistre.service.nearbyearthquakes.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaucimaistre.service.nearbyearthquakes.dto.GetEarthquakesByLocationResponse.EarthquakeResponse;
import com.gaucimaistre.service.nearbyearthquakes.model.Earthquake;

@Mapper
public interface EarthquakeResponseMapper {
        @Mapping(target = "title", expression = "java(earthquake.getTitle())")
        EarthquakeResponse mapToEarthquakeResponse(Earthquake earthquake);
}