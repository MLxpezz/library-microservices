package com.library.books_microservice.service;

import com.library.books_microservice.dto.BookDTO;

import java.util.List;

public interface BookService {

    List<BookDTO> getBooks();

    BookDTO getBookById(String bookId);

    BookDTO createBook(BookDTO book);

    String deleteBook(String id);

    BookDTO updateBook(String bookId, BookDTO book);

    BookDTO bookLoan(String bookId);

    BookDTO bookReturn(String bookId);
}
