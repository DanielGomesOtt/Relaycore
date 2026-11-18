package com.danielgomesott.relaycore.destination;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, UUID> {
    boolean existsByNameIgnoreCase(@NotBlank(message = "O nome do destino é obrigatório") String name);
}
