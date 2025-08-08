package com.library.api.v1.services;

import com.library.api.v1.modules.books.Book;
import com.library.api.v1.modules.books.dtos.BookRequestDTO;
import com.library.api.v1.modules.books.dtos.BookResponseDTO;
import com.library.api.v1.modules.books.dtos.UpdateBookDTO;

import java.util.List;

public interface BookService {
    BookResponseDTO create(BookRequestDTO bookRequestDTO);
    BookResponseDTO getBook(Long id);
    List<BookResponseDTO> getBooks(Long authorId, String title, String category, String state);
    List<Book> getBooksByIds(List<Long> bookIds);
    void returnBooks(List<Book> books);
    void rentBooks(List<Book> books);
    BookResponseDTO updateBook(Long id, UpdateBookDTO updateDTO);
    void deleteBook(Long id);
    void validateAvailabilityOfBooks(List<Book> books);
}
