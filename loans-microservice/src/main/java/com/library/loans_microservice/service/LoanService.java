package com.library.loans_microservice.service;

import com.library.loans_microservice.dto.CreateLoanDTO;
import com.library.loans_microservice.dto.LoanDTO;
import com.library.loans_microservice.dto.UpdateLoanDTO;
import com.library.loans_microservice.http.response.LoanByStudentAndBookResponse;

import java.util.List;

public interface LoanService {

    void save(CreateLoanDTO createLoanDTO);

    List<LoanDTO> getLoans();

    LoanDTO getLoan(Long id);

    String deleteLoan(Long id);

    LoanDTO updateLoan(Long id, UpdateLoanDTO updateLoanDTO);

    LoanByStudentAndBookResponse getLoanByStudentAndBook(CreateLoanDTO createLoanDTO);
}
