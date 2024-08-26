package com.library.api.authors.validations;

import com.library.api.authors.stubs.AuthorStub;
import com.library.api.books.stubs.BookStub;
import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.modules.authors.Author;
import com.library.api.modules.authors.validations.RemoveAuthorValidator;
import com.library.api.modules.books.Book;
import com.library.api.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


public class RemoveAuthorValidationTest {

    private Author authorStub;
    private Book bookStub;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private RemoveAuthorValidator removeAuthorValidator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        authorStub = AuthorStub.createAuthorStub();
        bookStub = BookStub.createBookStub();
    }

    @Test
    @DisplayName("[VALIDATION] Deve permitir a exclusão de um autor")
    public void shouldNotThrowExceptionWhenAuthorIsNotAssociatedWithBook() {
        removeAuthorValidator.validate(authorStub);
    }

    @Test
    @DisplayName("[VALIDATION] Deve lançar uma exceção ao tentar remover um autor com livros associados")
    public void shouldThrowBadRequestExceptionWhenAuthorIsAssociatedWithBook() {
        when(bookRepository.findByAuthorsContains(authorStub)).thenReturn(List.of(bookStub));
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> removeAuthorValidator.validate(authorStub));

        assertEquals(exception.getMessage(), "O Autor '" + authorStub.getName() + "' não pode ser removido pois possui livros vinculados a ele");
    }


}
