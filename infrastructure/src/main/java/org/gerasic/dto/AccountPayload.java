package org.gerasic.dto;

import java.math.BigDecimal;

public record AccountPayload(
        String owner,
        BigDecimal balance
) {}
