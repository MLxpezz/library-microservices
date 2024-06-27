package com.library.books_microservice.service;

import com.library.books_microservice.entities.BookEntity;

import java.util.List;

public interface BookService {

    List<BookEntity> getBooks();

    BookEntity getBookByTitle(String title);

    BookEntity createBook(BookEntity book);

    String deleteBook(Long id);

    BookEntity updateBook(String title, BookEntity book);
}
