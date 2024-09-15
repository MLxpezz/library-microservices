package com.library.loans_microservice.aspects;

import com.library.loans_microservice.entity.LoanEntity;
import com.library.loans_microservice.repository.LoanHistoryRepository;
import com.library.loans_microservice.repository.LoanRepository;
import com.library.loans_microservice.utils.LoanMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Aspect
@Slf4j
@Component
public class LoanHistoryAspect {

    private final LoanHistoryRepository loanHistoryRepository;

    private final LoanRepository loanRepository;

    public LoanHistoryAspect(LoanHistoryRepository loanHistoryRepository, LoanRepository loanRepository) {
        this.loanHistoryRepository = loanHistoryRepository;
        this.loanRepository = loanRepository;
    }

    @Before(value = "execution(* com.library.loans_microservice.service.LoanService.deleteLoan(..)) && args(id)", argNames = "id")
    public void addLoanToHistory(Long id) {

        LoanEntity loanEntity = loanRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Prestamo con id: " + id + " no encontrado."));

        loanHistoryRepository.save(LoanMapper.loanEntityToLoanHistoryEntity(loanEntity));
        log.info("Prestamo agregado al historial de prestamos, con fecha {}", LocalDate.now());
    }
}
