package com.danielgomesott.relaycore.destination;

public class DestinationAlreadyExistsException extends RuntimeException {
    public DestinationAlreadyExistsException(String message) {
        super(message);
    }
}
