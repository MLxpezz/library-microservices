package com.library.books_microservice.controller;

import com.library.books_microservice.entities.BookEntity;
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
    public ResponseEntity<List<BookEntity>> getAll() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping("/get/{title}")
    public ResponseEntity<BookEntity> getById(@PathVariable String title) {
        return ResponseEntity.ok(bookService.getBookByTitle(title));
    }

    @PostMapping("/create")
    public ResponseEntity<BookEntity> create(@RequestBody BookEntity book) {
        return ResponseEntity.ok(bookService.createBook(book));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.deleteBook(id));
    }
}
