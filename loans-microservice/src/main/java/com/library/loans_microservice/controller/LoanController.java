package com.library.loans_microservice.controller;

import com.library.loans_microservice.dto.CreateLoanDTO;
import com.library.loans_microservice.dto.LoanDTO;
import com.library.loans_microservice.exceptions.InsufficientBookStockException;
import com.library.loans_microservice.exceptions.MaxLoansReachedException;
import com.library.loans_microservice.http.response.LoanByStudentAndBookResponse;
import com.library.loans_microservice.service.LoanService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        return ResponseEntity.ok(loanService.getLoans());
    }

    @GetMapping("/get-info-loans")
    public ResponseEntity<?> getInfoLoans() {
        return ResponseEntity.ok(loanService.getReturningInfo());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<LoanDTO> getLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoan(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LoanDTO> updateLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.updateLoan(id));
    }

    @DeleteMapping("/return-book/{id}")
    public ResponseEntity<?> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of("Message: ", loanService.deleteLoan(id)));
    }

    @CircuitBreaker(name = "studentClient", fallbackMethod = "fallbackNewLoan")
    @PostMapping("/create")
    public ResponseEntity<?> newLoan(@RequestBody @Valid CreateLoanDTO createLoanDTO) {
        try {
            return ResponseEntity.ok(loanService.getLoanByStudentAndBook(createLoanDTO));
        } catch (MaxLoansReachedException | InsufficientBookStockException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public ResponseEntity<LoanByStudentAndBookResponse> fallbackNewLoan(CreateLoanDTO createLoanDTO, Throwable throwable) {
        LoanByStudentAndBookResponse response = LoanByStudentAndBookResponse
                .builder()
                .studentName("Servicio no disponible temporalmente, intente mas tarde.")
                .bookTitle("Servicio no disponible temporalmente, intente mas tarde.")
                .build();
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
