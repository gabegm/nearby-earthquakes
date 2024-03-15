package com.gaucimaistre.service.nearbyearthquakes.exception;

import java.io.IOException;

public class EarthquakeRetrievalFailedException extends RuntimeException {
    public EarthquakeRetrievalFailedException(RuntimeException runtimeException) {
        super(runtimeException);
    }

    public EarthquakeRetrievalFailedException(IOException ioException) {
        super(ioException);
    }
}