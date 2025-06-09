package org.gerasic.gateway.dto;

public record AuthRequest(
        String username,
        String password
) {}
