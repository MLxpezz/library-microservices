package com.library.students_microservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "students")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Column(name = "enrollment_number", nullable = false)
    private String enrollmentNumber;

    @Column(nullable = false)
    private String address;

    @Column(name = "id_loan")
    private Long idLoan;

    @Column(name = "count_loans")
    private byte countLoans;
}
