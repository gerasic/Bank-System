package org.gerasic.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import org.gerasic.gateway.model.users.Gender;
import org.gerasic.gateway.model.users.HairColor;


public record CreateUserRequestToClient(
        @NotBlank
        @JsonProperty("login") String login,
        @NotBlank
        @JsonProperty("name") String name,
        @Min(0)
        @JsonProperty("age") int age,
        @NotNull
        @JsonProperty("gender") Gender gender,
        @NotNull
        @JsonProperty("hairColor") HairColor hairColor
) {}
