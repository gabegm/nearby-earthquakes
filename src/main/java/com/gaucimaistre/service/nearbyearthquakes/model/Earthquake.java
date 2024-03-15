package com.gaucimaistre.service.nearbyearthquakes.model;

public record Earthquake(double magnitude, String address, int distance) {
    public String getTitle() {
        return "M " + magnitude + " - " + address + " || " + distance;
    }
}