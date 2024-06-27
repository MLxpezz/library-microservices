package com.library.books_microservice.service;

import com.library.books_microservice.entities.BookEntity;
import com.library.books_microservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<BookEntity> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public BookEntity getBookByTitle(String title) {
        return bookRepository.customFindByTitle(title).orElseThrow();
    }

    @Override
    public BookEntity createBook(BookEntity book) {
        return bookRepository.save(book);
    }

    @Override
    public String deleteBook(Long id) {
        bookRepository.deleteById(id.toString());
        return "Book deleted";
    }

    @Override
    public BookEntity updateBook(String title, BookEntity book) {
        Optional<BookEntity> bookEntity = bookRepository.customFindByTitle(title);

        if(bookEntity.isPresent()) {
            BookEntity updatedBook = bookEntity.get();
            updatedBook.setQuantity(book.getQuantity());
            return bookRepository.save(updatedBook);
        }

        return bookRepository.save(book);
    }
}
