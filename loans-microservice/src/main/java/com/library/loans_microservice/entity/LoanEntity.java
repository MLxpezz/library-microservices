package com.library.loans_microservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "loans")
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loan_date", nullable = false, columnDefinition = "DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate loanDate;

    @Column(name = "return_date", nullable = false, columnDefinition = "DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "book_id", nullable = false)
    private String bookId;

    public long daysPastDue() {
        long daysBetween = ChronoUnit.DAYS.between(this.returnDate, LocalDate.now());
        return daysBetween < 0 ? 0 : daysBetween;
    }

    public double penaltyAmount() {
        return this.daysPastDue() * 2.00;
    }
}
