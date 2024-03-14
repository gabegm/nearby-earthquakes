package com.gaucimaistre.service.nearbyearthquakes.exception;

public class EarthquakeRetrievalFailedException extends RuntimeException {
    public EarthquakeRetrievalFailedException(RuntimeException runtimeException) {
        super(runtimeException);
    }
}