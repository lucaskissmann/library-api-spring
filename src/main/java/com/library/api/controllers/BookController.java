package com.library.api.controllers;

import com.library.api.modules.books.dtos.UpdateBookDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.library.api.modules.books.dtos.BookRequestDTO;
import com.library.api.modules.books.dtos.BookResponseDTO;
import com.library.api.services.BookServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController
    extends
        Controller{

    private final BookServiceImpl bookService;

    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    private ResponseEntity<BookResponseDTO> create(@Valid @RequestBody BookRequestDTO dto) {
        return created(bookService.create(dto));
    }

    @GetMapping
    private ResponseEntity<List<BookResponseDTO>> getBooks(@RequestParam(required = false) Long authorId,
                                                           @RequestParam(required = false) String title) {
        return ok(bookService.getBooks(authorId, title));
    }

    @GetMapping("/{id}")
    private ResponseEntity<BookResponseDTO> getBook(@PathVariable Long id) {
        return ok(bookService.getBook(id));
    }

    @PutMapping("/{id}")
    private ResponseEntity<BookResponseDTO> update(@Valid @PathVariable Long id, @Valid @RequestBody UpdateBookDTO dto) {
        return ok(bookService.updateBook(id, dto));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<BookResponseDTO> delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return noContent();
    }
}
