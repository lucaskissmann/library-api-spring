package com.library.api.authors.validations;

import com.library.api.authors.stubs.AuthorStub;
import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.modules.authors.Author;
import com.library.api.modules.authors.validations.GenderValidation;
import com.library.api.modules.authors.validations.UniqueNameValidation;
import com.library.api.repositories.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class UniqueNameValidationTest {

    private Author createAuthorStub;
    private Author createAuthorNotUniqueName;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private UniqueNameValidation uniqueNameValidation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        createAuthorStub = AuthorStub.createAuthorStub();
        createAuthorNotUniqueName = AuthorStub.createAuthorNotUniqueName();
    }

    @Test
    @DisplayName("[VALIDATION] Não deve lançar nenhuma exception para um nome válido.")
    public void shouldNotThrowExceptionWhenNameIsValid() {
        when(authorRepository.findAuthorByName("Lucas")).thenReturn(Optional.empty());

        uniqueNameValidation.validate(createAuthorStub);
    }

    @Test
    @DisplayName("[VALIDATION] Deve lançar exception para um nome já registrado.")
    public void shouldThrowExceptionWhenThereIsAnotherRegisterWithSameName() {
        when(authorRepository.findAuthorByName("Lucas")).thenReturn(Optional.of(createAuthorStub));

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> uniqueNameValidation.validate(createAuthorNotUniqueName));

        assertEquals(exception.getMessage(), "Autor já cadastrado para o nome 'Lucas'");
    }

    @Test
    @DisplayName("[VALIDATION] Não deve lançar exception caso o nome duplicado seja do próprio solicitante.")
    public void shouldNotThrowExceptionWhenDuplicateNameBelongsToTheAuthorItself() {
        when(authorRepository.findAuthorByName("Lucas")).thenReturn(Optional.of(createAuthorNotUniqueName));

        uniqueNameValidation.validate(createAuthorNotUniqueName);
    }
}
