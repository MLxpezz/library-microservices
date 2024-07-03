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

    @GetMapping("/get/{title}")
    public ResponseEntity<BookDTO> getById(@PathVariable String title) {
        return ResponseEntity.ok(bookService.getBookByTitle(title));
    }

    @PostMapping("/create")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO book) {
        return ResponseEntity.ok(bookService.createBook(book));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    @PutMapping("/update/{title}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable String title, @RequestBody BookDTO book) {
        return ResponseEntity.ok(bookService.updateBook(title, book));
    }

}
