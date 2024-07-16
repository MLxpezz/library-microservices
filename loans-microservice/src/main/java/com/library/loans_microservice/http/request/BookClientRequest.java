package com.library.loans_microservice.http.request;

import com.library.loans_microservice.dto.BookDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

@HttpExchange
public interface BookClientRequest {

    @GetExchange("/get/{bookId}")
    public BookDTO findBookById(@PathVariable String bookId);

    @PutExchange("/update/{bookid}")
    public BookDTO updateBook(@PathVariable String bookId, @RequestBody BookDTO bookDTO);
}
