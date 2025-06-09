package org.gerasic.gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.gerasic.gateway.model.transactions.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;


@JsonIgnoreProperties(ignoreUnknown = true)
public record TransactionResponse(
        @JsonProperty("accountId") String accountId,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("type") TransactionType type,
        @JsonProperty("timestamp") LocalDateTime timestamp
) {
    public TransactionResponse {
        Objects.requireNonNull(accountId, "accountId cannot be null");
        Objects.requireNonNull(amount, "amount cannot be null");
        Objects.requireNonNull(type, "type cannot be null");
        Objects.requireNonNull(timestamp, "timestamp cannot be null");
    }
}