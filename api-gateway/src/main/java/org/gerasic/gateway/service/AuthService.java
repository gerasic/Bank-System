// src/main/java/org/gerasic/gateway/service/AuthService.java
package org.gerasic.gateway.service;

import org.gerasic.gateway.dao.persistance.entity.UserEntity;
import org.gerasic.gateway.dao.persistance.repository.UserRepository;
import org.gerasic.gateway.dto.AuthRequest;
import org.gerasic.gateway.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    public String authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        UserDetails user = userDetailsService.loadUserByUsername(request.username());
        return jwtService.generateToken(user);
    }
}
