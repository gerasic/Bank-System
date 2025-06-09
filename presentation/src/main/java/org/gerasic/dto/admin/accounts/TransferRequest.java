package org.gerasic.dto.admin.accounts;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record TransferRequest(
        @NotNull(message = "From Account ID is required")
        Long fromAccountId,

        @NotNull(message = "To Account ID is required")
        Long toAccountId,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        BigDecimal amount
) {}
