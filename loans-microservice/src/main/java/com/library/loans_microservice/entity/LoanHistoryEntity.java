package com.library.loans_microservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "History")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoanHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "started_loan")
    private LocalDate startedLoan;

    @Column(nullable = false, name = "ended_loan")
    private LocalDate endedLoan;

    @Column(nullable = false, name = "student_id")
    private Long studentId;

    @Column(nullable = false, name = "book_id")
    private String bookId;

    @Column(nullable = false)
    private String status;
}
