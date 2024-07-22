package com.library.auth_microservice.dto;

import lombok.Builder;

@Builder
public record AuthResponseDTO (String token, String message){
}
