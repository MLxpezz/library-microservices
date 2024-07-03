package com.library.loans_microservice.controller;

import com.library.loans_microservice.dto.UpdateLoanDTO;
import com.library.loans_microservice.entity.LoanEntity;
import com.library.loans_microservice.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping("/get-all")
    public ResponseEntity<List<LoanEntity>> getAllLoans() {
        return ResponseEntity.ok(loanService.getLoans());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<LoanEntity> getLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoan(id));
    }

    @PostMapping("/create")
    public ResponseEntity<LoanEntity> createLoan(@RequestBody LoanEntity loanEntity) {
        return ResponseEntity.ok(loanService.save(loanEntity));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.deleteLoan(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LoanEntity> updateLoan(@PathVariable Long id, @RequestBody UpdateLoanDTO updateLoanDTO) {
        return ResponseEntity.ok(loanService.updateLoan(id, updateLoanDTO));
    }
}
