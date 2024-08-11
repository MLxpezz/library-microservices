package com.library.loans_microservice.aspects;

import com.library.loans_microservice.dto.BookDTO;
import com.library.loans_microservice.dto.CreateLoanDTO;
import com.library.loans_microservice.dto.StudentDTO;
import com.library.loans_microservice.entity.LoanEntity;
import com.library.loans_microservice.http.request.BookClientRequest;
import com.library.loans_microservice.http.request.StudentClientRequest;
import com.library.loans_microservice.repository.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoanUpdateServicesAspect {

    Logger logger = LoggerFactory.getLogger(LoanUpdateServicesAspect.class);

    private final StudentClientRequest studentClient;

    private final BookClientRequest bookClient;

    private final LoanRepository loanRepository;

    public LoanUpdateServicesAspect(StudentClientRequest studentClient, BookClientRequest bookClient, LoanRepository loanRepository) {
        this.studentClient = studentClient;
        this.bookClient = bookClient;
        this.loanRepository = loanRepository;
    }

    @AfterReturning(value = "execution(* com.library.loans_microservice.service.LoanService.getLoanByStudentAndBook(..)) && args(createLoanDTO)", argNames = "createLoanDTO")
    public void updateStudentAndBook(CreateLoanDTO createLoanDTO) {
        try {
            // Obtener el ID del estudiante y del libro desde createLoanDTO
            Long studentId = createLoanDTO.studentId();
            String bookId = createLoanDTO.bookId();

            // Actualizar al estudiante para incrementar la cantidad de préstamos
            StudentDTO student = studentClient.studentLoan(studentId);

            // Actualizar la cantidad de existencias del libro
            BookDTO book = bookClient.bookLoan(bookId);

            logger.info("El estudiante {} ahora tiene {} préstamos y el libro '{}' tiene {} existencias.",
                    student.name(), student.countLoans(), book.title(), book.quantity());
        } catch (Exception e) {
            logger.error("Error al actualizar el estudiante o el libro después de crear el préstamo: ", e);
        }
    }

    @Before(value = "execution(* com.library.loans_microservice.service.LoanService.deleteLoan(..)) && args(id)", argNames = "id")
    public void updateStudentAndBookAfterDeleteLoan(Long id) {

        LoanEntity loan = loanRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Prestamo con id: " + id + " no encontrado."));

        try {

            StudentDTO student = studentClient.studentReturnook(loan.getStudentId());
            BookDTO book = bookClient.bookReturn(loan.getBookId());

            logger.info("Devolucion exista!, el estudiante {} ahora tiene {} prestamos y el libro {} tiene {} existencias.",
                    student.name(), student.countLoans(), book.title(), book.quantity());


        } catch (Exception e) {
            logger.info("Ocurrio un error al intentar actualizar al estudiante y el libro");
        }
    }
}
