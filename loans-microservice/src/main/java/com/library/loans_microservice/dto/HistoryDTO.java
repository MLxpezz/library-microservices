package com.library.loans_microservice.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record HistoryDTO(
        Long id,
        LocalDate startLoanDate,
        LocalDate finishLoanDate,
        String enrollmentNumber,
        String bookTitle
) {
}
