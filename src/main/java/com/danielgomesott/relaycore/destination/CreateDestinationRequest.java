package com.danielgomesott.relaycore.destination;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import java.util.List;

public record CreateDestinationRequest(
        @NotBlank(message = "O nome do destino é obrigatório")
        String name,

        @NotBlank(message = "A URL é obrigatória")
        @URL(message = "Formato da URL inválido")
        String url,

        @Min(value = 100, message = "O timeout mínimo é de 100ms")
        @Max(value = 30000, message = "O timeout máximo é de 30000ms (30s)")
        Integer timeoutMs,

        @Min(value = 1, message = "Deve haver pelo menos 1 tentativa")
        @Max(value = 10, message = "O número máximo de tentativas permitido é 10")
        Integer maxAttempts,

        String secretRef,

        @NotEmpty(message = "O destino deve assinar pelo menos um tipo de evento")
        List<String> subscribedEvents
) {

}
