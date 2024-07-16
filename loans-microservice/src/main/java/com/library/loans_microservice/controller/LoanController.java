package com.library.loans_microservice.controller;

import com.library.loans_microservice.dto.CreateLoanDTO;
import com.library.loans_microservice.dto.LoanDTO;
import com.library.loans_microservice.dto.UpdateLoanDTO;
import com.library.loans_microservice.entity.LoanEntity;
import com.library.loans_microservice.http.request.BookClientRequest;
import com.library.loans_microservice.http.request.StudentClientRequest;
import com.library.loans_microservice.http.response.LoanByStudentAndBookResponse;
import com.library.loans_microservice.service.LoanService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private BookClientRequest bookClient;

    @Autowired
    private StudentClientRequest studentClient;

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

    //@CircuitBreaker(name = "newLoan", fallbackMethod = "fallbackNewLoan")
    @PostMapping("/create")
    public ResponseEntity<LoanByStudentAndBookResponse> newLoan(@RequestBody CreateLoanDTO createLoanDTO) {
        return ResponseEntity.ok(loanService.getLoanByStudentAndBook(createLoanDTO));
    }

    @GetMapping("/get-book/{bookId}")
    public ResponseEntity<?> getBook(@PathVariable String bookId) {
        return ResponseEntity.ok(bookClient.findBookById(bookId));
    }

    @GetMapping("/get-student/{studentId}")
    public ResponseEntity<?> getStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentClient.getStudentById(studentId));
    }

    /*public ResponseEntity<LoanByStudentAndBookResponse> fallbackNewLoan(CreateLoanDTO createLoanDTO, Throwable throwable) {
        LoanByStudentAndBookResponse response = LoanByStudentAndBookResponse
                .builder()
                .studentName("Servicio no disponible temporalmente, intente mas tarde.")
                .bookTitle("Servicio no disponible temporalmente, intente mas tarde.")
                .build();
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }*/
}
