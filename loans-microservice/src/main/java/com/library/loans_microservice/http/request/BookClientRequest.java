package com.library.loans_microservice.http.request;

import com.library.loans_microservice.dto.BookDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

@HttpExchange
public interface BookClientRequest {

    @GetExchange("/get/{bookId}")
    public BookDTO findBookById(@PathVariable String bookId);

    @PutExchange("/book-loan/{bookId}")
    public BookDTO bookLoan(@PathVariable String bookId);

    @PutExchange("/book-return/{bookId}")
    public BookDTO bookReturn(@PathVariable String bookId);
}
