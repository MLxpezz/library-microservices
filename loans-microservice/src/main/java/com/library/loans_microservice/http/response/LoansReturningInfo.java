package com.library.loans_microservice.http.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record LoansReturningInfo(
        Long idLoan,
        String studentName,
        String bookTitle,
        LocalDate returningDate,
        long daysAfterReturningDate,
        double penaltyAmount
) {
}
