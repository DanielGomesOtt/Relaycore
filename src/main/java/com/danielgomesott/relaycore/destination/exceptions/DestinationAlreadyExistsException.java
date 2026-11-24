package com.danielgomesott.relaycore.destination.exceptions;

public class DestinationAlreadyExistsException extends RuntimeException {
    public DestinationAlreadyExistsException(String message) {
        super(message);
    }
}
