package com.library.loans_microservice.service;

import com.library.loans_microservice.dto.*;
import com.library.loans_microservice.entity.LoanEntity;
import com.library.loans_microservice.http.request.BookClientRequest;
import com.library.loans_microservice.http.request.StudentClientRequest;
import com.library.loans_microservice.http.response.LoanByStudentAndBookResponse;
import com.library.loans_microservice.repository.LoanRepository;
import com.library.loans_microservice.utils.LoanMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
public class LoanServiceImpl implements LoanService{

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookClientRequest bookClient;

    @Autowired
    private StudentClientRequest studentClient;

    @Override
    public LoanEntity save(CreateLoanDTO createLoanDTO) {
        LoanEntity loanEntity = LoanMapper.dtoToEntity(createLoanDTO);
        return loanRepository.save(loanEntity);
    }

    @Override
    public List<LoanDTO> getLoans() {
        return LoanMapper.entityListToDtoList(loanRepository.findAll());
    }

    @Override
    public LoanDTO getLoan(Long id) {
        LoanEntity loanEntity = loanRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Prestamo con id: " + id + " no encontrado"));
        return LoanMapper.entityToDto(loanEntity);
    }

    @Override
    public String deleteLoan(Long id) {
        loanRepository.deleteById(id);
        return "Prestamo con id: " + id + " eliminado";
    }

    @Override
    public LoanDTO updateLoan(Long id) {
        LoanEntity loanEntity = loanRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Prestamo con id: " + id + " no encontrado"));
        loanEntity.setReturnDate(LocalDate.now().plusDays(7));
        return LoanMapper.entityToDto(loanRepository.save(loanEntity));
    }

    @Override
    public LoanByStudentAndBookResponse getLoanByStudentAndBook(CreateLoanDTO createLoanDTO) {

        //buscamos primero el estudiante
        StudentDTO studentDTO = studentClient.getStudentById(createLoanDTO.studentId());

        //buscamos el libro que pidio prestado
        BookDTO bookDTO = bookClient.findBookById(createLoanDTO.bookId());

        //guardo el prestamo en la base de datos
        LoanEntity newLoan = this.save(createLoanDTO);

        //construimos y retornamos el objeto del prestamo
        return LoanByStudentAndBookResponse
                .builder()
                .bookTitle(bookDTO.title())
                .studentName(studentDTO.name() + " " + studentDTO.lastname())
                .returnDate(newLoan.getReturnDate())
                .build();
    }
}
