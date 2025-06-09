package org.gerasic.gateway.dao.clients;

import org.gerasic.gateway.dto.TransactionResponse;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Map;

@Repository
public class TransactionClient {
    private final WebClient webClient;

    public TransactionClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8080").build();
    }

    public TransactionResponse deposit(Long accountId, BigDecimal amount) {
        return webClient.post()
                .uri("/api/admin/accounts/deposit", accountId)
                .bodyValue(Map.of("accountId", accountId, "amount", amount))
                .retrieve()
                .bodyToMono(TransactionResponse.class)
                .block();
    }

    public TransactionResponse withdraw(Long accountId, BigDecimal amount) {
        return webClient.post()
                .uri("/api/admin/accounts/withdraw", accountId)
                .bodyValue(Map.of("accountId", accountId, "amount", amount))
                .retrieve()
                .bodyToMono(TransactionResponse.class)
                .block();
    }

    public TransactionResponse transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        return webClient.post()
                .uri("/api/admin/accounts/transfer")
                .bodyValue(Map.of(
                        "fromAccountId", fromAccountId,
                        "toAccountId", toAccountId,
                        "amount", amount
                ))
                .retrieve()
                .bodyToMono(TransactionResponse.class)
                .block();
    }
}
