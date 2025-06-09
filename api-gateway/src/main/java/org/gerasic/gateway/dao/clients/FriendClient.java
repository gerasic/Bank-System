package org.gerasic.gateway.dao.clients;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Repository
public class FriendClient {
    private final WebClient webClient;

    public FriendClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8080").build();
    }

    public void addFriend(String userLogin, String friendLogin) {
        webClient.post()
                .uri("/api/admin/users/friendships")
                .bodyValue(Map.of("login", userLogin, "friendLogin", friendLogin))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void removeFriend(String userLogin, String friendLogin) {
        webClient
                .method(HttpMethod.DELETE)
                .uri("/api/admin/users/friendships")
                .bodyValue(Map.of("login", userLogin, "friendLogin", friendLogin))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
