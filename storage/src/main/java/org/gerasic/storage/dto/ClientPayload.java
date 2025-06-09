package org.gerasic.storage.dto;

public record ClientPayload(
        String login,
        String name,
        int age,
        String gender,
        String hairColor
) {}
