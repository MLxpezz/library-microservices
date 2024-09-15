package com.library.loans_microservice.service;

import com.library.loans_microservice.entity.LoanHistoryEntity;
import com.library.loans_microservice.repository.LoanHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanHistoryServiceImpl implements LoanHistoryService{

    private final LoanHistoryRepository loanHistoryRepository;

    public LoanHistoryServiceImpl(LoanHistoryRepository loanHistoryRepository) {
        this.loanHistoryRepository = loanHistoryRepository;
    }

    @Override
    public void addLoan(LoanHistoryEntity loanHistory) {
        this.loanHistoryRepository.save(loanHistory);
    }

    @Override
    public List<LoanHistoryEntity> getAllLoanHistory() {
        return this.loanHistoryRepository.findAll();
    }
}
