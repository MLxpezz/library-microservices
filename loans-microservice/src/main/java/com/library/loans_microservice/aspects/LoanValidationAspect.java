package com.library.loans_microservice.aspects;

import com.library.loans_microservice.dto.BookDTO;
import com.library.loans_microservice.dto.CreateLoanDTO;
import com.library.loans_microservice.dto.StudentDTO;
import com.library.loans_microservice.http.request.BookClientRequest;
import com.library.loans_microservice.http.request.StudentClientRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoanValidationAspect {

    Logger logger = LoggerFactory.getLogger(LoanValidationAspect.class);

    @Autowired
    private StudentClientRequest studentClient;

    @Autowired
    private BookClientRequest bookClient;

    /*@Before("execution(* com.library.loans_microservice.service.LoanService.getLoanByStudentAndBook(..)) && args (createLoanDTO)")
    public void validateLoanRequest(JoinPoint joinPoint, CreateLoanDTO createLoanDTO) {

        //Verifico primero si el estudiante tiene menos de los 4 prestamos permitidos
        StudentDTO student = studentClient.getStudentById(createLoanDTO.studentId());
        if(student.getCountLoans() >= 4) {
            throw new IllegalStateException("El estudiante ya tiene el maximo de prestamos permitidos.");
        }

        //Si el estudiante puede pedir prestado verifico si hay libros todavia en existencia
        BookDTO book = bookClient.findBookById(createLoanDTO.bookId());
        if(book.quantity() == 0) {
            throw new IllegalStateException("El libro: " + book.title() + " no tiene existencias.");
        }

        logger.info("El prestamo se realizo con exito al alumno {} y del libro {}", student.getName(), book.title());
    }*/
}
