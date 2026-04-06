package com.gaucimaistre.service.nearbyearthquakes.exception;

import java.io.IOException;

import org.springframework.web.client.RestClientException;

public class EarthquakeRetrievalFailedException extends RuntimeException {
    public EarthquakeRetrievalFailedException(RestClientException restClientException) {
        super(restClientException);
    }

    public EarthquakeRetrievalFailedException(IOException ioException) {
        super(ioException);
    }
}
