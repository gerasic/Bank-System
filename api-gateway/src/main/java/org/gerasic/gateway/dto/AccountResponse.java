package org.gerasic.gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.gerasic.gateway.model.transactions.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AccountResponse(
        @JsonProperty("id") Long id,
        @JsonProperty("ownerLogin") String ownerLogin,
        @JsonProperty("balance") BigDecimal balance,
        @JsonProperty("transactionHistory") List<Transaction> transactionHistory
) {
    public AccountResponse {
        Objects.requireNonNull(id, "id cannot be null");
        Objects.requireNonNull(ownerLogin, "ownerLogin cannot be null");
        Objects.requireNonNull(balance, "balance cannot be null");
        if (transactionHistory == null) {
            transactionHistory = List.of();
        }
    }
}
