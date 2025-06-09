package org.gerasic.gateway.dao.clients;

import org.gerasic.gateway.dto.AccountResponse;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountClient {

    private final WebClient webClient;

    public AccountClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8080")
                .build();
    }

    public List<AccountResponse> findAllAccounts() {
        return webClient.get()
                .uri("/api/admin/accounts")
                .retrieve()
                .bodyToFlux(AccountResponse.class)
                .collectList()
                .block();
    }

    public List<AccountResponse> findByUserLogin(String login) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/admin/accounts")
                        .queryParam("ownerLogin", login)
                        .build())
                .retrieve()
                .bodyToFlux(AccountResponse.class)
                .collectList()
                .block();
    }

    public AccountResponse findAccountById(Long id) {
        return webClient.get()
                .uri("/api/admin/accounts/{id}", id)
                .retrieve()
                .bodyToMono(AccountResponse.class)
                .block();
    }
}
