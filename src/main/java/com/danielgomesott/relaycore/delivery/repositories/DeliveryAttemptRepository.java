package com.danielgomesott.relaycore.delivery.repositories;

import com.danielgomesott.relaycore.delivery.entities.DeliveryAttempts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeliveryAttemptRepository extends JpaRepository<DeliveryAttempts, UUID> {
}
