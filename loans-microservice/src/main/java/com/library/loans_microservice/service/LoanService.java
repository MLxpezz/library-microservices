package com.library.loans_microservice.service;

import com.library.loans_microservice.dto.CreateLoanDTO;
import com.library.loans_microservice.dto.LoanDTO;
import com.library.loans_microservice.entity.LoanEntity;
import com.library.loans_microservice.http.response.LoanByStudentAndBookResponse;

import java.util.List;

public interface LoanService {

    LoanEntity save(CreateLoanDTO createLoanDTO);

    List<LoanDTO> getLoans();

    LoanDTO getLoan(Long idLoan);

    String deleteLoan(Long idLoan);

    LoanDTO updateLoan(Long idLoan);

    LoanByStudentAndBookResponse getLoanByStudentAndBook(CreateLoanDTO createLoanDTO);
}
