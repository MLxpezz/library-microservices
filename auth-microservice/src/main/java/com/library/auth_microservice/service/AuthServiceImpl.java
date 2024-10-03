package com.library.auth_microservice.service;

import com.library.auth_microservice.config.jwt.JwtUtils;
import com.library.auth_microservice.dto.AuthResponseDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponseDTO login(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            String token = jwtUtils.createToken(authentication);

            return AuthResponseDTO.builder()
                    .message("Autenticación exitosa!")
                    .token(token)
                    .isSuccess(true)
                    .build();

        } catch (Exception ex) {
            System.err.println("Error en la autenticación: " + ex.getMessage());
            throw new BadCredentialsException("Credenciales incorrectas");
        }
    }

}
