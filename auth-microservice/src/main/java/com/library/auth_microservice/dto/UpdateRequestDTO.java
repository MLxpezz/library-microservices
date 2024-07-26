package com.library.auth_microservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UpdateRequestDTO(@NotBlank(message = "El campo correo es requerido.") @Email(message = "El correo es invalido.")
                               String email,
                               @NotBlank(message = "El campo contraseña es requerido.")
                               @Size(min = 8, message = "El campo contraseña debe tener un minimo de 8 caracteres.")
                               String password) {
}
