package com.danielgomesott.relaycore.delivery;

public enum DeliveryStatus {
    PENDING,
    PROCESSING,
    RETRY_SCHEDULED,
    SUCCEEDED,
    DEAD,
    CANCELLED
}
