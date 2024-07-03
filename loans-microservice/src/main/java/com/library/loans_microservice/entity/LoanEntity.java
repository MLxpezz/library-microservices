package com.library.loans_microservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @Column(name = "loan_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate loanDate;

    @Column(name = "return_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;

    private Long studentId;
}
