package org.gerasic.dto;

import org.gerasic.users.Gender;
import org.gerasic.users.HairColor;

public record ClientPayload(
        String login,
        String name,
        int age,
        Gender gender,
        HairColor hairColor
) {}
