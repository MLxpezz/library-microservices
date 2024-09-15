package com.library.loans_microservice.aspects;

import com.library.loans_microservice.dto.BookDTO;
import com.library.loans_microservice.dto.CreateLoanDTO;
import com.library.loans_microservice.dto.StudentDTO;
import com.library.loans_microservice.exceptions.InsufficientBookStockException;
import com.library.loans_microservice.exceptions.MaxLoansReachedException;
import com.library.loans_microservice.exceptions.RepeatBookException;
import com.library.loans_microservice.http.request.BookClientRequest;
import com.library.loans_microservice.http.request.StudentClientRequest;
import com.library.loans_microservice.repository.LoanRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoanValidationAspect {

    Logger logger = LoggerFactory.getLogger(LoanValidationAspect.class);

    private final StudentClientRequest studentClient;

    private final BookClientRequest bookClient;

    private final LoanRepository loanRepository;

    public LoanValidationAspect(StudentClientRequest studentClient, BookClientRequest bookClient, LoanRepository loanRepository) {
        this.studentClient = studentClient;
        this.bookClient = bookClient;
        this.loanRepository = loanRepository;
    }

    @Before("execution(* com.library.loans_microservice.service.LoanService.getLoanByStudentAndBook(..)) && args (createLoanDTO)")
    public void validateLoanRequest(CreateLoanDTO createLoanDTO) {

        //Verifico primero si el estudiante tiene menos de los 4 prestamos permitidos
        StudentDTO student = studentClient.getStudentById(createLoanDTO.studentId());
        if(student.countLoans() >= 4) {
            throw new MaxLoansReachedException("El estudiante ya tiene el maximo de prestamos permitidos.");
        }

        //Si el estudiante puede pedir prestado verifico si hay libros todavia en existencia
        BookDTO book = bookClient.findBookById(createLoanDTO.bookId());
        if(book.quantity() <= 0) {
            throw new InsufficientBookStockException("El libro: " + book.title() + " no tiene existencias.");
        }

        //verificar que el libro que pedira prestado no sea uno que ya tenga
        loanRepository.findAll().forEach(loan -> {
            if(loan.getBookId().equals(book.id()) && loan.getStudentId().equals(student.id())) {
                throw new RepeatBookException("El alumno ya tiene el libro " + book.title());
            }
        });

        logger.info("El prestamo se realizo con exito al alumno {} y del libro {}", student.name(), book.title());
    }
}
