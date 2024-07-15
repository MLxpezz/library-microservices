package com.library.loans_microservice.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record LoanDTO(Long id, LocalDate loanDate, LocalDate returnDate, Long studentId, String bookId) {
}
