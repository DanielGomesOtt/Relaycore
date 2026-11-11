package com.danielgomesott.relaycore.destination;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DestinationSubscriptionRepository extends JpaRepository<DestinationSubscription, UUID> {
}
