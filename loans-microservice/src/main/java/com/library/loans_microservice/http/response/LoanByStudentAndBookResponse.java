package com.library.loans_microservice.http.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record LoanByStudentAndBookResponse(String bookTitle, String studentName, LocalDate returnDate) {
}
