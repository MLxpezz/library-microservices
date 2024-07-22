package com.library.books_microservice.controller;

import com.library.books_microservice.dto.BookDTO;
import com.library.books_microservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/get-all")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping("/get/{bookId}")
    public ResponseEntity<BookDTO> getById(@PathVariable String bookId) {
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    @PostMapping("/create")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO book) {
        return ResponseEntity.ok(bookService.createBook(book));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable String bookId, @RequestBody BookDTO book) {
        return ResponseEntity.ok(bookService.updateBook(bookId, book));
    }

    @PutMapping("/book-loan/{bookId}")
    public ResponseEntity<BookDTO> bookLoan(@PathVariable String bookId) {
        return ResponseEntity.ok(bookService.bookLoan(bookId));
    }

    @PutMapping("/book-return/{bookId}")
    public ResponseEntity<BookDTO> bookReturn(@PathVariable String bookId) {
        return ResponseEntity.ok(bookService.bookReturn(bookId));
    }

}
