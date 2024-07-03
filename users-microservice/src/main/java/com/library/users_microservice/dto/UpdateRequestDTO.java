package com.library.users_microservice.dto;

import lombok.Builder;

@Builder
public record UpdateRequestDTO(String email, String password) {
}
