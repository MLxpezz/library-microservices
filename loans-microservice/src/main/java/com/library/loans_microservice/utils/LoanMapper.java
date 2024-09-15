package com.library.loans_microservice.utils;

import com.library.loans_microservice.dto.BookDTO;
import com.library.loans_microservice.dto.CreateLoanDTO;
import com.library.loans_microservice.dto.LoanDTO;
import com.library.loans_microservice.dto.StudentDTO;
import com.library.loans_microservice.entity.LoanEntity;
import com.library.loans_microservice.entity.LoanHistoryEntity;
import com.library.loans_microservice.http.request.BookClientRequest;
import com.library.loans_microservice.http.request.StudentClientRequest;

import java.time.LocalDate;
import java.util.List;

public class LoanMapper {

    public static LoanEntity dtoToEntity(CreateLoanDTO dto) {
        return LoanEntity
                .builder()
                .loanDate(LocalDate.now())
                .returnDate(LocalDate.now().plusDays(7))
                .studentId(dto.studentId())
                .bookId(dto.bookId())
                .build();
    }

    public static LoanDTO entityToDto(LoanEntity entity, StudentDTO student, BookDTO book) {
        return LoanDTO
                .builder()
                .id(entity.getId())
                .returnDate(entity.getReturnDate())
                .studentName(student.name())
                .bookTitle(book.title())
                .build();
    }

    public static List<LoanDTO> entityListToDtoList(List<LoanEntity> entityList, StudentClientRequest studentClient, BookClientRequest bookClient) {
        return entityList
                .stream()
                .map(loan -> {
                    StudentDTO student = studentClient.getStudentById(loan.getStudentId());
                    return LoanDTO
                            .builder()
                            .id(loan.getId())
                            .returnDate(loan.getReturnDate())
                            .studentName(student.name() + " " + student.lastname())
                            .bookTitle(bookClient.findBookById(loan.getBookId()).title())
                            .build();
                })
                .toList();
    }

    public static LoanHistoryEntity loanEntityToLoanHistoryEntity(LoanEntity loanEntity) {
        return LoanHistoryEntity
                .builder()
                .startedLoan(loanEntity.getLoanDate())
                .endedLoan(LocalDate.now())
                .bookId(loanEntity.getBookId())
                .studentId(loanEntity.getStudentId())
                .status("DEVUELTO")
                .build();
    }
}
