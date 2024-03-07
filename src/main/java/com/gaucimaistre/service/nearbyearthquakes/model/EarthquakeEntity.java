package com.gaucimaistre.service.nearbyearthquakes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name="earthquake")
@Table(name="earthquake")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
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

    private int distance;
}
