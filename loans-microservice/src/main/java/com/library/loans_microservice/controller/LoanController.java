package com.library.loans_microservice.controller;

import com.library.loans_microservice.dto.CreateLoanDTO;
import com.library.loans_microservice.dto.LoanDTO;
import com.library.loans_microservice.dto.UpdateLoanDTO;
import com.library.loans_microservice.entity.LoanEntity;
import com.library.loans_microservice.http.response.LoanByStudentAndBookResponse;
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
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        return ResponseEntity.ok(loanService.getLoans());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<LoanDTO> getLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoan(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.deleteLoan(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LoanDTO> updateLoan(@PathVariable Long id, @RequestBody UpdateLoanDTO updateLoanDTO) {
        return ResponseEntity.ok(loanService.updateLoan(id, updateLoanDTO));
    }

    @PostMapping("/create")
    public ResponseEntity<LoanByStudentAndBookResponse> newLoan(@RequestBody CreateLoanDTO createLoanDTO) {
        return ResponseEntity.ok(loanService.getLoanByStudentAndBook(createLoanDTO));
    }
}
