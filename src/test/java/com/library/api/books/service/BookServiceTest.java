package com.library.api.books.service;

import com.library.api.authors.stubs.AuthorStub;
import com.library.api.books.stubs.BookStub;
import com.library.api.helpers.exceptions.NotFoundException;
import com.library.api.modules.books.Book;
import com.library.api.modules.books.dtos.BookRequestDTO;
import com.library.api.modules.books.dtos.BookResponseDTO;
import com.library.api.modules.books.dtos.UpdateBookDTO;
import com.library.api.modules.books.enums.BookState;
import com.library.api.modules.books.validations.RemoveBookValidator;
import com.library.api.repositories.BookRepository;
import com.library.api.services.AuthorServiceImpl;
import com.library.api.services.BookServiceImpl;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@DisplayName("BookService")
public class BookServiceTest {

    Book bookStub = BookStub.createBookStub();
    BookRequestDTO bookRequestStub = BookStub.createBookRequestDTO();
    BookRequestDTO invalidBookRequestDTO = BookStub.createInvalidBookRequestDTO();
    BookResponseDTO bookResponseStub = BookStub.createBookResponseDTO();
    UpdateBookDTO updateBookStub = BookStub.updateBookDTO();

    private Validator validator;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorServiceImpl authorService;

    @Mock
    private RemoveBookValidator removeBookValidator;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("[SERVICE] Deve criar um livro")
    public void shouldCreateABook() {
        when(authorService.getAuthorsByIds(bookRequestStub.getAuthorIds())).thenReturn(List.of(AuthorStub.createAuthorStub()));
        BookResponseDTO dto = bookService.create(bookRequestStub);

        assertEquals(bookRequestStub.getTitle(), dto.getTitle());
        assertEquals(bookRequestStub.getIsbn().replaceAll("\\D", ""), dto.getIsbn());
        assertEquals(bookRequestStub.getPublicationDate(), dto.getPublicationDate());
        assertEquals(bookRequestStub.getAuthorIds().get(0), dto.getAuthors().get(0).getId());
        assertEquals(BookState.AVAILABLE, dto.getState());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("[SERVICE] Não deve criar um livro para dados inválidos")
    public void shouldNotCreateABook() {
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(invalidBookRequestDTO);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("[SERVICE] Deve lançar uma exceção ao não encontrar livro para o ID informado")
    public void shouldNotFindBookByID() {
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> bookService.getBook(1L));

        assertEquals("Livro não encontrado para o ID: #" + 1L, exception.getMessage());
    }

    @Test
    @DisplayName("[SERVICE] Deve encontrar um livro para o ID informado")
    public void shouldFindBookByID() {
        when(bookRepository.findById(bookStub.getId())).thenReturn(Optional.of(bookStub));

        BookResponseDTO foundBook = bookService.getBook(bookStub.getId());

        assertNotNull(foundBook);
        assertEquals(foundBook.getId(), bookResponseStub.getId());
        assertEquals(foundBook.getTitle(), bookResponseStub.getTitle());
        assertEquals(foundBook.getIsbn(), bookResponseStub.getIsbn());
        assertEquals(foundBook.getPublicationDate(), bookResponseStub.getPublicationDate());
        assertEquals(foundBook.getState(), bookResponseStub.getState());
    }

    @Test
    @DisplayName("[SERVICE] Deve encontrar uma lista de livros")
    public void shouldFindAllBooks() {
        when(bookRepository.findByAuthorAndTitle(null, null)).thenReturn(List.of(bookStub));

        List<BookResponseDTO> foundBooks = bookService.getBooks(null, null);

        assertNotNull(foundBooks);
        assertEquals(foundBooks.size(), 1);
        assertEquals(foundBooks.get(0).getId(), bookStub.getId());
    }

    @Test
    @DisplayName("[SERVICE] Deve atualizar um livro")
    public void shouldUpdateABook() {
        Long bookId = 1L;
        when(bookRepository.findById(bookStub.getId())).thenReturn(Optional.of(bookStub));
        when(bookRepository.save(bookStub)).thenReturn(bookStub);
        when(authorService.getAuthorsByIds(updateBookStub.getAuthorIds())).thenReturn(List.of(AuthorStub.createAuthorStub(), AuthorStub.createAuthorStub()));

        BookResponseDTO result = bookService.updateBook(bookId, updateBookStub);

        assertNotNull(result);
        assertEquals(result.getId(), bookStub.getId());
        assertEquals(result.getTitle(), updateBookStub.getTitle());
        assertEquals(result.getPublicationDate(), updateBookStub.getPublicationDate());
        assertEquals(result.getAuthors().size(), updateBookStub.getAuthorIds().size());

        verify(bookRepository).findById(bookId);
        verify(bookRepository).save(bookStub);
    }
}
