package com.gaucimaistre.service.nearbyearthquakes.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "earthquake")
@Table(name = "earthquake")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class EarthquakeEntity {
    @Id
    @Column
    private String id;

    @Column
    private String place;

    @Column
    private double magnitude;

    @Column
    private double latitude;

    @Column
    private double longitude;

    @Column
    private Instant time;

    private int distance;
}
