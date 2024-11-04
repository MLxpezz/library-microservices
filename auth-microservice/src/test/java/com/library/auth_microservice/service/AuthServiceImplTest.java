package com.library.auth_microservice.service;

import com.library.auth_microservice.TestDataProvider;
import com.library.auth_microservice.config.jwt.JwtUtils;
import com.library.auth_microservice.dto.AuthResponseDTO;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    @Order(1)
    public void loginTest() {
        String email = "mauricio@gmail.com";
        String password = "12345678";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(TestDataProvider.authenticationProvider());
        when(jwtUtils.createToken(TestDataProvider.authenticationProvider())).thenReturn(anyString());

        AuthResponseDTO authResponseDTO = authService.login(email, password);

        assertNotNull(authResponseDTO);
        assertNotNull(authResponseDTO.token());
        verify(authenticationManager, times(1)).authenticate(TestDataProvider.authenticationProvider());
        verify(jwtUtils, times(1)).createToken(TestDataProvider.authenticationProvider());
        System.out.println("Test Login successful");
    }

    @Test
    @Order(2)
    public void LoginBadCredentialsTest() {
        String email = "incorrectEmail@gmail.com";
        String password = "incorrectPassword";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException(""));

        assertThrows(BadCredentialsException.class, () -> {
            authService.login(email, password);
        });
        System.out.println("Test BadCredentialsException successful");
    }
}
