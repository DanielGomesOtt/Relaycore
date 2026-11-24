package com.danielgomesott.relaycore.destination.repositories;

import com.danielgomesott.relaycore.destination.entities.DestinationSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DestinationSubscriptionRepository extends JpaRepository<DestinationSubscription, UUID> {
}
