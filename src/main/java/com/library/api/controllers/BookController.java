package com.library.api.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.library.api.modules.books.dtos.BookRequestDTO;
import com.library.api.modules.books.dtos.BookResponseDTO;
import com.library.api.services.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController
    extends
        Controller{

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    private ResponseEntity<BookResponseDTO> create(@Valid @RequestBody BookRequestDTO dto) {
        return created(bookService.create(dto));
    }

    @GetMapping
    private ResponseEntity<List<BookResponseDTO>> getBooks() {
        return ok(bookService.getBooks());
    }
}
