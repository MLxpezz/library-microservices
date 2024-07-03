package com.library.users_microservice.dto;

import lombok.Builder;

@Builder
public record UserDTO(Long id, String email) {
}
