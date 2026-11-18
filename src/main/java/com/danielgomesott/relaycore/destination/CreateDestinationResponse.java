package com.danielgomesott.relaycore.destination;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record CreateDestinationResponse(
        UUID id,
        String name,
        String url,
        Boolean enabled,
        Integer timeoutMs,
        Integer maxAttempts,
        String secretRef,
        List<String> subscribedEvents,
        Instant createdAt
) {

    public static CreateDestinationResponse fromEntity(Destination destination) {
        List<String> events = destination.getSubscriptions()
                .stream()
                .map(DestinationSubscription::getEventType)
                .toList();
        return new CreateDestinationResponse(
                destination.getId(),
                destination.getName(),
                destination.getUrl(),
                destination.getEnabled(),
                destination.getTimeoutMs(),
                destination.getMaxAttempts(),
                destination.getSecretRef(),
                events,
                destination.getCreatedAt()
        );
    }

}
