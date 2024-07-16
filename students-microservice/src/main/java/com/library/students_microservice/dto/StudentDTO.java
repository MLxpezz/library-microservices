package com.library.students_microservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record StudentDTO(Long id,
                         @NotBlank(message = "El campo es requerido.") String name,
                         @NotBlank(message = "El campo es requerido.") String lastname,
                         @NotBlank(message = "El campo es requerido.") @Email(message = "El correo es invalido.") String email,
                         @NotBlank(message = "El campo es requerido.") String phone,
                         @NotBlank(message = "El campo es requerido.") String enrollmentNumber,
                         @NotBlank(message = "El campo es requerido.") String address,
                         byte countLoans) {
}
