package com.library.loans_microservice.utils;

import com.library.loans_microservice.dto.CreateLoanDTO;
import com.library.loans_microservice.dto.LoanDTO;
import com.library.loans_microservice.entity.LoanEntity;

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

    public static LoanDTO entityToDto(LoanEntity entity) {
        return LoanDTO
                .builder()
                .id(entity.getId())
                .loanDate(entity.getLoanDate())
                .returnDate(entity.getReturnDate())
                .studentId(entity.getStudentId())
                .bookId(entity.getBookId())
                .build();
    }

    public static List<LoanDTO> entityListToDtoList(List<LoanEntity> entityList) {
        return entityList
                .stream()
                .map(LoanMapper::entityToDto)
                .toList();
    }
}
