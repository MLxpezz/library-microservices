package com.library.loans_microservice.aspects;

import com.library.loans_microservice.dto.BookDTO;
import com.library.loans_microservice.dto.CreateLoanDTO;
import com.library.loans_microservice.dto.StudentDTO;
import com.library.loans_microservice.entity.LoanEntity;
import com.library.loans_microservice.http.request.BookClientRequest;
import com.library.loans_microservice.http.request.StudentClientRequest;
import com.library.loans_microservice.http.response.LoanByStudentAndBookResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
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

    @AfterReturning(value = "execution(* com.library.loans_microservice.service.LoanService.getLoanByStudentAndBook(..)) && args(createLoanDTO)", returning = "loanByStudentAndBookResponse", argNames = "createLoanDTO,loanByStudentAndBookResponse")
    public void updateStudentAndBook(CreateLoanDTO createLoanDTO, LoanByStudentAndBookResponse loanByStudentAndBookResponse) {
        try {
            // Obtener el ID del estudiante y del libro desde createLoanDTO
            Long studentId = createLoanDTO.studentId();
            String bookId = createLoanDTO.bookId();

            // Actualizar al estudiante para incrementar la cantidad de préstamos
            StudentDTO student = studentClient.getStudentById(studentId);
            StudentDTO updatedStudent = StudentDTO.builder()
                    .id(student.id())
                    .address(student.address())
                    .countLoans((byte) (student.countLoans() + 1))
                    .email(student.email())
                    .phone(student.phone())
                    .enrollmentNumber(student.enrollmentNumber())
                    .name(student.name())
                    .lastname(student.lastname())
                    .build();
            studentClient.updateStudentById(student.id(), updatedStudent);

            // Actualizar la cantidad de existencias del libro
            BookDTO book = bookClient.findBookById(bookId);
            BookDTO updatedBook = BookDTO.builder()
                    .id(book.id())
                    .title(book.title())
                    .author(book.author())
                    .isbn(book.isbn())
                    .quantity((byte) (book.quantity() - 1))
                    .build();
            bookClient.updateBook(book.id(), updatedBook);

            logger.info("El estudiante {} ahora tiene {} préstamos y el libro '{}' tiene {} existencias.",
                    student.name(), updatedStudent.countLoans(), book.title(), updatedBook.quantity());
        } catch (Exception e) {
            logger.error("Error al actualizar el estudiante o el libro después de crear el préstamo: ", e);
        }
    }
}
