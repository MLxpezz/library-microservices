package com.library.books_microservice.service;

import com.library.books_microservice.dto.BookDTO;

import java.util.List;

public interface BookService {

    List<BookDTO> getBooks();

    BookDTO getBookByTitle(String title);

    BookDTO createBook(BookDTO book);

    String deleteBook(Long id);

    BookDTO updateBook(String title, BookDTO book);
}
