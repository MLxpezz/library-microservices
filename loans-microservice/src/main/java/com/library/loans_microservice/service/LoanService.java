package com.library.loans_microservice.service;

import com.library.loans_microservice.dto.UpdateLoanDTO;
import com.library.loans_microservice.entity.LoanEntity;

import java.time.LocalDate;
import java.util.List;

public interface LoanService {

    LoanEntity save(LoanEntity loanEntity);

    List<LoanEntity> getLoans();

    LoanEntity getLoan(Long id);

    String deleteLoan(Long id);

    LoanEntity updateLoan(Long id, UpdateLoanDTO updateLoanDTO);
}
