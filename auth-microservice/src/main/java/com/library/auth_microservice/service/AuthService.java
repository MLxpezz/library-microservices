package com.library.auth_microservice.service;

import com.library.auth_microservice.dto.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO login(String email, String password);
}
