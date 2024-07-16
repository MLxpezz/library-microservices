package com.library.loans_microservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateLoanDTO(@NotNull(message = "El id del estudiante no debe ser nulo") Long studentId,
                            @NotBlank(message = "El id del libro no debe ser nulo") String bookId) {
}
