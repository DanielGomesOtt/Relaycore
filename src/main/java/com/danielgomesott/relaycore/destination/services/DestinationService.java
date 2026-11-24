package com.danielgomesott.relaycore.destination.services;

import com.danielgomesott.relaycore.destination.entities.DestinationSubscription;
import com.danielgomesott.relaycore.destination.dto.CreateDestinationRequest;
import com.danielgomesott.relaycore.destination.dto.CreateDestinationResponse;
import com.danielgomesott.relaycore.destination.entities.Destination;
import com.danielgomesott.relaycore.destination.exceptions.DestinationAlreadyExistsException;
import com.danielgomesott.relaycore.destination.repositories.DestinationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DestinationService {

    private static final Logger log = LoggerFactory.getLogger(DestinationService.class);

    private DestinationRepository destinationRepository;

    public DestinationService (DestinationRepository destinationRepository){
        this.destinationRepository = destinationRepository;
    };

    public CreateDestinationResponse create (CreateDestinationRequest request) {

        if(destinationRepository.existsByNameIgnoreCase(request.name())) {
            log.warn("Tentativa de cadastrar destino com nome duplicado: {}", request.name());
            throw new DestinationAlreadyExistsException("Já existe um destino cadastrado com o nome: " + request.name());
        }


        Destination destination = new Destination();
        destination.setName(request.name());
        destination.setUrl(request.url());
        destination.setEnabled(true);
        destination.setTimeoutMs(request.timeoutMs() != null ? request.timeoutMs() : 5000);
        destination.setMaxAttempts(request.maxAttempts() != null ? request.maxAttempts() : 5);
        destination.setSecretRef(request.secretRef());

        if (request.subscribedEvents() != null) {
            for (String eventType : request.subscribedEvents()) {
                DestinationSubscription subscription = new DestinationSubscription(destination, eventType.trim());
                destination.getSubscriptions().add(subscription);
            }
        }

        Destination savedDestination = destinationRepository.save(destination);
        log.info("Destino cadastrado com sucesso! ID: {}, Eventos assinados: {}",
                savedDestination.getId(), savedDestination.getSubscriptions().size());

        return CreateDestinationResponse.fromEntity(savedDestination);
    }

}
