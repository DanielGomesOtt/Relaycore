package com.danielgomesott.relaycore.delivery.repositories;

import com.danielgomesott.relaycore.delivery.entities.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
}
