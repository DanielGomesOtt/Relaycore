package com.danielgomesott.relaycore.messaging.repositories;

import com.danielgomesott.relaycore.messaging.entities.OutboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OutboxMessageRepository extends JpaRepository<OutboxMessage, UUID> {
}
