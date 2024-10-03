package com.library.loans_microservice.service;

import com.library.loans_microservice.dto.*;
import com.library.loans_microservice.entity.LoanEntity;
import com.library.loans_microservice.http.request.BookClientRequest;
import com.library.loans_microservice.http.request.StudentClientRequest;
import com.library.loans_microservice.http.response.LoanByStudentAndBookResponse;
import com.library.loans_microservice.http.response.LoansReturningInfo;
import com.library.loans_microservice.repository.LoanRepository;
import com.library.loans_microservice.utils.LoanMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class LoanServiceImpl implements LoanService{

    private final LoanRepository loanRepository;

    private final BookClientRequest bookClient;

    private final StudentClientRequest studentClient;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public LoanServiceImpl(LoanRepository loanRepository, BookClientRequest bookClient, StudentClientRequest studentClient, KafkaTemplate<String, String> kafkaTemplate) {
        this.loanRepository = loanRepository;
        this.bookClient = bookClient;
        this.studentClient = studentClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public LoanEntity save(CreateLoanDTO createLoanDTO) {
        LoanEntity loanEntity = LoanMapper.dtoToEntity(createLoanDTO);
        return loanRepository.save(loanEntity);
    }

    @Override
    public List<LoanDTO> getLoans() {
        return LoanMapper.entityListToDtoList(loanRepository.findAll(), studentClient, bookClient);
    }

    @Override
    public LoanDTO getLoan(Long id) {
        LoanEntity loanEntity = loanRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Prestamo con id: " + id + " no encontrado"));
        return LoanMapper.entityToDto(loanEntity, getStudent(loanEntity.getStudentId()), getBook(loanEntity.getBookId()));
    }

    @Override
    public String deleteLoan(Long id) {
        loanRepository.deleteById(id);
        return "Prestamo con id: " + id + " eliminado";
    }

    @Override
    public LoanDTO updateLoan(Long id) {
        LoanEntity loanEntity = loanRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Prestamo con id: " + id + " no encontrado"));
        loanEntity.setReturnDate(loanEntity.getReturnDate().plusDays(7));
        return LoanMapper.entityToDto(loanRepository.save(loanEntity), getStudent(loanEntity.getStudentId()), getBook(loanEntity.getBookId()));
    }



    @Override
    public LoanByStudentAndBookResponse getLoanByStudentAndBook(CreateLoanDTO createLoanDTO) {

        //buscamos primero el estudiante
        StudentDTO studentDTO = studentClient.getStudentById(createLoanDTO.studentId());

        //buscamos el libro que pidio prestado
        BookDTO bookDTO = bookClient.findBookById(createLoanDTO.bookId());

        //guardo el prestamo en la base de datos
        LoanEntity newLoan = this.save(createLoanDTO);

        //enviamos evento con kafka al microservicio de notificaciones para avisar al usuario por correo de su prestamo
        kafkaTemplate.send("loanNotification", studentDTO.email());

        //construimos y retornamos el objeto del prestamo
        return LoanByStudentAndBookResponse
                .builder()
                .bookTitle(bookDTO.title())
                .studentName(studentDTO.name() + " " + studentDTO.lastname())
                .returnDate(newLoan.getReturnDate())
                .build();
    }

    @Override
    public List<LoansReturningInfo> getReturningInfo() {

        return loanRepository.findAll()
                .stream()
                .map(loan -> {
                    StudentDTO student = getStudent(loan.getStudentId());
                    return LoansReturningInfo
                            .builder()
                            .idLoan(loan.getId())
                            .bookTitle(getBook(loan.getBookId()).title())
                            .studentName(student.name() + " " + student.lastname())
                            .daysAfterReturningDate(loan.daysPastDue())
                            .penaltyAmount(loan.penaltyAmount())
                            .returningDate(loan.getReturnDate())
                            .build();
                }).toList();
    }

    public StudentDTO getStudent(Long idStudent) {
        return studentClient.getStudentById(idStudent);
    }

    public BookDTO getBook(String idBook) {
        return bookClient.findBookById(idBook);
    }
}
