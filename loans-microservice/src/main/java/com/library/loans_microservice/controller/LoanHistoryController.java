package com.library.loans_microservice.controller;

import com.library.loans_microservice.service.LoanHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/history")
public class LoanHistoryController {

    private final LoanHistoryService loanHistoryService;

    public LoanHistoryController(LoanHistoryService loanHistoryService) {
        this.loanHistoryService = loanHistoryService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllLoansHistory() {
        return ResponseEntity.ok(loanHistoryService.getAllLoanHistory());
    }
}
