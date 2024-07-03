package com.library.users_microservice.dto;

import lombok.Builder;

@Builder
public record LoginRequestDTO(String email, String password) {
}
