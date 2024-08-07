package com.library.loans_microservice.http.request;

import com.library.loans_microservice.dto.BookDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

@HttpExchange
public interface BookClientRequest {

    @GetExchange("/get/{bookId}")
    BookDTO findBookById(@PathVariable String bookId);

    @PutExchange("/book-loan/{bookId}")
    BookDTO bookLoan(@PathVariable String bookId);

    @PutExchange("/book-return/{bookId}")
    BookDTO bookReturn(@PathVariable String bookId);
}
