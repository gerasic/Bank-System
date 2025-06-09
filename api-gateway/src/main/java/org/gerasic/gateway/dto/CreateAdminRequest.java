package org.gerasic.gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateAdminRequest(
        @NotBlank
        @JsonProperty("login") String login,
        @NotBlank
        @JsonProperty("password") String password,
        @NotBlank
        @JsonProperty("name") String name
) {}
