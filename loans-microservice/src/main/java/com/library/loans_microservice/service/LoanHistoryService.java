package com.library.loans_microservice.service;

import com.library.loans_microservice.entity.LoanHistoryEntity;

import java.util.List;

public interface LoanHistoryService {

    void addLoan(LoanHistoryEntity loanHistory);

    List<LoanHistoryEntity> getAllLoanHistory();
}
