-- =============================================================================
-- TABELA 1: EVENTS (Eventos recebidos pela API)
-- =============================================================================
CREATE TABLE events (
    id VARCHAR(36) PRIMARY KEY,
    event_type VARCHAR(100) NOT NULL,
    payload JSONB NOT NULL,
    idempotency_key VARCHAR(150),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP WITH TIME ZONE
);

-- Garante que o mesmo event_type com a mesma idempotency_key não seja duplicado
CREATE UNIQUE INDEX uk_events_type_idempotency_key
    ON events(event_type, idempotency_key)
    WHERE idempotency_key IS NOT NULL;

CREATE INDEX idx_events_created_at ON events(created_at);


-- =============================================================================
-- TABELA 2: DESTINATIONS (Configuração dos destinos HTTP de webhooks)
-- =============================================================================
CREATE TABLE destinations (
      id VARCHAR(36) PRIMARY KEY,
      name VARCHAR(100) NOT NULL,
      url VARCHAR(500) NOT NULL,
      enabled BOOLEAN NOT NULL DEFAULT TRUE,
      timeout_ms INTEGER NOT NULL DEFAULT 5000,
      max_attempts INTEGER NOT NULL DEFAULT 5,
      secret_ref VARCHAR(255),
      created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- =============================================================================
-- TABELA 3: DELIVERIES (Estado de cada entrega Evento x Destino)
-- =============================================================================
CREATE TABLE deliveries (
    id VARCHAR(36) PRIMARY KEY,
    event_id VARCHAR(36) NOT NULL,
    destination_id VARCHAR(36) NOT NULL,
    status VARCHAR(30) NOT NULL, -- PENDING, PROCESSING, RETRY_SCHEDULED, SUCCEEDED, DEAD, CANCELLED
    attempt_count INTEGER NOT NULL DEFAULT 0,
    next_attempt_at TIMESTAMP WITH TIME ZONE,
    locked_until TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_deliveries_event FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
    CONSTRAINT fk_deliveries_destination FOREIGN KEY (destination_id) REFERENCES destinations(id) ON DELETE CASCADE,
    CONSTRAINT uk_deliveries_event_destination UNIQUE (event_id, destination_id)
);

-- Índice chave para o worker encontrar tarefas prontas para execução / retentativa
CREATE INDEX idx_deliveries_status_next_attempt ON deliveries(status, next_attempt_at);
CREATE INDEX idx_deliveries_locked_until ON deliveries(locked_until);


-- =============================================================================
-- TABELA 4: DELIVERY_ATTEMPTS (Histórico de cada tentativa de envio)
-- =============================================================================
CREATE TABLE delivery_attempts (
   id VARCHAR(36) PRIMARY KEY,
   delivery_id VARCHAR(36) NOT NULL,
   attempt_number INTEGER NOT NULL,
   http_status INTEGER,
   duration_ms BIGINT,
   error_code VARCHAR(100),
   attempted_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

   CONSTRAINT fk_attempts_delivery FOREIGN KEY (delivery_id) REFERENCES deliveries(id) ON DELETE CASCADE
);

CREATE INDEX idx_delivery_attempts_delivery_id ON delivery_attempts(delivery_id, attempt_number);


-- =============================================================================
-- TABELA 5: OUTBOX_MESSAGES (Padrão Transactional Outbox para publicação confiável)
-- =============================================================================
CREATE TABLE outbox_messages (
     id VARCHAR(36) PRIMARY KEY,
     aggregate_id VARCHAR(36) NOT NULL,
     message_type VARCHAR(100) NOT NULL,
     payload_ref VARCHAR(255),
     created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
     published_at TIMESTAMP WITH TIME ZONE
);

-- Índice chave para o Outbox Publisher localizar rapidamente mensagens pendentes de envio
CREATE INDEX idx_outbox_published_created ON outbox_messages(published_at, created_at);