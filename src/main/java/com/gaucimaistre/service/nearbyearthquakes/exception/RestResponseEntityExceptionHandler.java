package com.gaucimaistre.service.nearbyearthquakes.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { EarthquakeRetrievalFailedException.class })
    protected ResponseEntity<Object> handleEarthquakeRetrievalFailedException(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                "Unable to fetch data from downstream API",
                new HttpHeaders(),
                HttpStatus.FAILED_DEPENDENCY,
                request);
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleException(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                "Failed to compute request",
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }
}