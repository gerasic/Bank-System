package org.gerasic.dto.admin.users;

import jakarta.validation.constraints.*;

public record FriendshipRequest(
        @NotBlank(message = "Login is required")
        @Size(min = 5, max = 25, message = "Login length must be from 5 to 25")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only letters and numbers allowed")
        String login,

        @NotBlank(message = "Friend's login is required")
        @Size(min = 5, max = 25, message = "Friend's login length must be from 5 to 25")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only letters and numbers allowed")
        String friendLogin
) {}
