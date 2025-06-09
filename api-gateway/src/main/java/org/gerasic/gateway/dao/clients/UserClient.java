package org.gerasic.gateway.dao.clients;

import org.gerasic.gateway.dto.CreateUserRequest;
import org.gerasic.gateway.dto.CreateUserRequestToClient;
import org.gerasic.gateway.dto.UserResponse;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Repository
public class UserClient {

    private final WebClient webClient;

    public UserClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public void createUser(CreateUserRequest user) {
        CreateUserRequestToClient req = new CreateUserRequestToClient(user.login(), user.name(), user.age(), user.gender(), user.hairColor());
        webClient.post()
                .uri("/api/admin/users")
                .bodyValue(req)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public List<UserResponse> findAll(String gender, String hairColor) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/admin/users")
                        .queryParam("gender", gender)
                        .queryParam("hairColor", hairColor)
                        .build())
                .retrieve()
                .bodyToFlux(UserResponse.class)
                .collectList()
                .block();
    }

    public Optional<UserResponse> findByLogin(String login) {
        try {
            return Optional.ofNullable(webClient.get()
                    .uri("/api/admin/users/{login}", login)
                    .retrieve()
                    .bodyToMono(UserResponse.class)
                    .block());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
