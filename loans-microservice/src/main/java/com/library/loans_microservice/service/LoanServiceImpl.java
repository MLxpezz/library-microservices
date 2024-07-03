package com.library.loans_microservice.service;

import com.library.loans_microservice.dto.UpdateLoanDTO;
import com.library.loans_microservice.entity.LoanEntity;
import com.library.loans_microservice.repository.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService{

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public LoanEntity save(LoanEntity loanEntity) {
        return loanRepository.save(loanEntity);
    }

    @Override
    public List<LoanEntity> getLoans() {
        return loanRepository.findAll();
    }

    @Override
    public LoanEntity getLoan(Long id) {
        return loanRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Prestamo con id: " + id + " no encontrado"));
    }

    @Override
    public String deleteLoan(Long id) {
        loanRepository.deleteById(id);
        return "Prestamo con id: " + id + " eliminado";
    }

    @Override
    public LoanEntity updateLoan(Long id, UpdateLoanDTO updateLoanDTO) {
        LoanEntity loanEntity = loanRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Prestamo con id: " + id + " no encontrado"));
        loanEntity.setReturnDate(updateLoanDTO.returnDate());
        return loanRepository.save(loanEntity);
    }
}
