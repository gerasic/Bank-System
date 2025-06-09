package org.gerasic.dto.admin.users;

import jakarta.validation.constraints.*;
import org.gerasic.users.Gender;
import org.gerasic.users.HairColor;

public record CreateUserRequest(
        @NotBlank(message = "Login is required")
        @Size(min = 5, max = 25, message = "Login length must be from 5 to 25")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only letters and numbers allowed")
        String login,

        @NotBlank(message = "Name is required")
        @Size(min = 5, max = 25, message = "Name length must be from 5 to 25")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "Only letters allowed")
        String name,

        @Min(value = 0, message = "Age must be 0 or more")
        int age,

        @NotNull(message = "Gender is required")
        Gender gender,

        @NotNull(message = "Hair color is required")
        HairColor hairColor
) {}
