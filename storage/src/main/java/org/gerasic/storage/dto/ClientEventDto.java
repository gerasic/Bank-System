package org.gerasic.storage.dto;

import java.time.Instant;

public record ClientEventDto(
        String clientId,
        String action,
        Instant timestamp,
        ClientPayload payload
) {}
