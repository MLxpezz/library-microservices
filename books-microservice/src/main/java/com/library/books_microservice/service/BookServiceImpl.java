package com.library.books_microservice.service;

import com.library.books_microservice.dto.BookDTO;
import com.library.books_microservice.entities.BookEntity;
import com.library.books_microservice.repository.BookRepository;
import com.library.books_microservice.utils.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<BookDTO> getBooks() {
        return BookMapper.listEntityToDto(bookRepository.findAll());
    }

    @Override
    public BookDTO getBookById(String bookId) {
        BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow();
        return BookMapper.entityToDto(bookEntity);
    }

    @Override
    public BookDTO createBook(BookDTO book) {
        BookEntity newBook = BookMapper.dtoToEntity(book);
        return BookMapper.entityToDto(bookRepository.save(newBook));
    }

    @Override
    public String deleteBook(Long id) {
        bookRepository.deleteById(id.toString());
        return "Book with id: " + id + " deleted";
    }

    @Override
    public BookDTO updateBook(String bookId, BookDTO book) {
        Optional<BookEntity> bookEntity = bookRepository.customFindByTitle(bookId);

        if(bookEntity.isPresent()) {
            BookEntity updatedBook = bookEntity.get();
            updatedBook.setQuantity(book.quantity());
            return BookMapper.entityToDto(bookRepository.save(updatedBook));
        }

        BookEntity newBook = BookMapper.dtoToEntity(book);
        return BookMapper.entityToDto(bookRepository.save(newBook));
    }
}
