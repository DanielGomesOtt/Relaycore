package com.danielgomesott.relaycore.destination.controllers;

import com.danielgomesott.relaycore.destination.services.DestinationService;
import com.danielgomesott.relaycore.destination.dto.CreateDestinationRequest;
import com.danielgomesott.relaycore.destination.dto.CreateDestinationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/destinations")
@Tag(name = "Destinations", description = "Endpoints para gerenciamento dos destinos de webhooks")
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo destino", description = "Registra um novo endpoint HTTP com suas configurações de entrega e eventos inscritos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Destino criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
        @ApiResponse(responseCode = "409", description = "Já existe um destino com este nome")
    })
    public ResponseEntity<CreateDestinationResponse> create(
            @RequestBody @Valid CreateDestinationRequest request,
            UriComponentsBuilder uriBuilder
    ) {

        CreateDestinationResponse response = destinationService.create(request);

        URI location = uriBuilder
                .path("/v1/destinations/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

}
