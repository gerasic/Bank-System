package org.gerasic.dto;

import java.time.Instant;

public record AccountEventDto(
        String accountId,
        String action,
        Instant timestamp,
        AccountPayload payload
) {}
