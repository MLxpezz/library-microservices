package com.library.loans_microservice.dto;

import lombok.Builder;

@Builder
public record CreateLoanDTO(Long studentId, String bookId) {
}
