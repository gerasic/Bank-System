package org.gerasic.gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.gerasic.gateway.model.users.Gender;
import org.gerasic.gateway.model.users.HairColor;

import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserResponse(
        String login,
        String name,
        int age,
        Gender gender,
        HairColor hairColor,
        Set<String> friendLogins
) {
    public UserResponse {
        Objects.requireNonNull(login, "login cannot be null");
        Objects.requireNonNull(name, "name cannot be null");
        Objects.requireNonNull(gender, "gender cannot be null");
        Objects.requireNonNull(hairColor, "hairColor cannot be null");
        if (friendLogins == null) {
            friendLogins = Set.of();
        }
    }
}
