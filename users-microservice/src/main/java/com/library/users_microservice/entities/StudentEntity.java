package com.library.users_microservice.entities;

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

    private String name;

    private String lastname;

    private String email;

    private String phone;

    @Column(name = "enrollment_number")
    private String enrollmentNumber;

    private String address;

    @Column(name = "id_loan")
    private Long idLoan;

    @Column(name = "count_loans")
    private byte countLoans;
}
