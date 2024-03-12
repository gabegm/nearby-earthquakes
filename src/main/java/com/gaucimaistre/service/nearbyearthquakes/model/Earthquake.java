package com.gaucimaistre.service.nearbyearthquakes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class Earthquake {
    private final double magnitude;
    private final String address;
    private final int distance;

    public final String getTitle() {
        return "M " +
            String.valueOf(magnitude) +
            " - " +
            address +
            " || " +
            String.valueOf(distance);
    }
}
