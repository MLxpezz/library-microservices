package com.library.loans_microservice.aspects;

import com.library.loans_microservice.dto.BookDTO;
import com.library.loans_microservice.dto.StudentDTO;
import com.library.loans_microservice.entity.LoanEntity;
import com.library.loans_microservice.http.request.BookClientRequest;
import com.library.loans_microservice.http.request.StudentClientRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoanUpdateServicesAspect {

    Logger logger = LoggerFactory.getLogger(LoanUpdateServicesAspect.class);

    @Autowired
    private StudentClientRequest studentClient;

    @Autowired
    private BookClientRequest bookClient;

    /*@AfterReturning(value = "execution(* com.library.loans_microservice.service.LoanService.save(..))", returning = "loanEntity")
    public void updateStudentAndBook(JoinPoint joinPoint, LoanEntity loanEntity) {

        //actualizo al estudiante para aumentarle la cantidad de prestamos actuales
        StudentDTO student = studentClient.getStudentById(loanEntity.getStudentId());
        student.setCountLoans((byte) (student.getCountLoans() + 1));
        studentClient.updateStudentById(student.getId(), student);

        //actualizo la cantidad de existencia que tiene el libro que fue prestado al alumno, restandole 1 a su cantidad actual
        BookDTO bookDTO = bookClient.findBookById(loanEntity.getBookId());
        BookDTO updatedBook = BookDTO
                .builder()
                .id(bookDTO.id())
                .title(bookDTO.title())
                .author(bookDTO.author())
                .isbn(bookDTO.isbn())
                .quantity((byte) (bookDTO.quantity() - 1))
                .build();
        bookClient.updateBook(bookDTO.id(), updatedBook);

        logger.info("El estudiante {} ahora tiene {} prestamos y el libro {} tiene {} existencias.", student.getName(), student.getCountLoans(), bookDTO.title(), bookDTO.quantity());
    }*/
}
