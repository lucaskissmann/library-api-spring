package com.library.api.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.api.modules.books.dtos.BookRequestDTO;
import com.library.api.modules.books.dtos.BookResponseDTO;
import com.library.api.services.BookService;

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
}
