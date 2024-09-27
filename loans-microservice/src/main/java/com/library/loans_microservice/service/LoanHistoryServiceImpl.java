package com.library.loans_microservice.service;

import com.library.loans_microservice.dto.BookDTO;
import com.library.loans_microservice.dto.HistoryDTO;
import com.library.loans_microservice.dto.StudentDTO;
import com.library.loans_microservice.entity.LoanHistoryEntity;
import com.library.loans_microservice.http.request.BookClientRequest;
import com.library.loans_microservice.http.request.StudentClientRequest;
import com.library.loans_microservice.repository.LoanHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LoanHistoryServiceImpl implements LoanHistoryService{

    private final LoanHistoryRepository loanHistoryRepository;
    private final StudentClientRequest studentClientRequest;
    private final BookClientRequest bookClientRequest;

    public LoanHistoryServiceImpl(LoanHistoryRepository loanHistoryRepository, StudentClientRequest studentClientRequest, BookClientRequest bookClientRequest) {
        this.loanHistoryRepository = loanHistoryRepository;
        this.studentClientRequest = studentClientRequest;
        this.bookClientRequest = bookClientRequest;
    }

    @Override
    public void addLoan(LoanHistoryEntity loanHistory) {
        this.loanHistoryRepository.save(loanHistory);
    }

    @Override
    public List<HistoryDTO> getAllLoanHistory() {
        return this.loanHistoryRepository
                .findAll()
                .stream()
                .map(loan -> {
                    return HistoryDTO
                            .builder()
                            .id(loan.getId())
                            .finishLoanDate(loan.getEndedLoan())
                            .startLoanDate(loan.getStartedLoan())
                            .enrollmentNumber(getStudent(loan.getStudentId()).enrollmentNumber())
                            .bookTitle(getBook(loan.getBookId()).title())
                            .build();
                }).toList();
    }

    public StudentDTO getStudent(Long id) {
        return studentClientRequest.getStudentById(id);
    }

    public BookDTO getBook(String id) {
        return bookClientRequest.findBookById(id);
    }
}
