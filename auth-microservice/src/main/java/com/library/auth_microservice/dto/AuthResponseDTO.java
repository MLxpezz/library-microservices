package com.library.auth_microservice.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record AuthResponseDTO (String token, String message, boolean isSuccess){
}
