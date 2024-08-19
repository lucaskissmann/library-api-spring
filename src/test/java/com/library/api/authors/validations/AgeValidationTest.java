package com.library.api.authors.validations;

import com.library.api.authors.stubs.AuthorStub;
import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.modules.authors.Author;
import com.library.api.modules.authors.validations.AgeValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
public class AgeValidationTest {

    private Author createAuthorStub;
    private Author createAuthorTooOld;
    private Author createAuthorTooYoung;
    private Author createAuthorAgeNotANumber;

    private final AgeValidation ageValidation = new AgeValidation();

    @BeforeEach
    public void setUp() {
        createAuthorStub = AuthorStub.createAuthorStub();
        createAuthorTooOld = AuthorStub.createAuthorTooOld();
        createAuthorTooYoung = AuthorStub.createAuthorTooYoung();
        createAuthorAgeNotANumber = AuthorStub.createAuthorAgeNotANumber();
    }

    @Test
    @DisplayName("[VALIDATION] Não deve lançar nenhuma exception para uma idade válida.")
    public void shouldNotThrowExceptionWhenAgeIsValid() {
        ageValidation.validate(createAuthorStub);
    }

    @Test
    @DisplayName("[VALIDATION] Deve jogar uma exception ao receber uma idade acima do limite")
    public void shouldThrowExceptionAuthorTooOld() {
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> ageValidation.validate(createAuthorTooOld));

        assertEquals(exception.getMessage(), "A idade do Autor deve estar entre 18 e 120.");
    }

    @Test
    @DisplayName("[VALIDATION] Deve jogar uma exception ao receber uma idade abaixo do limite")
    public void shouldThrowExceptionAuthorTooYoung() {
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> ageValidation.validate(createAuthorTooYoung));

        assertEquals(exception.getMessage(), "A idade do Autor deve estar entre 18 e 120.");
    }

    @Test
    @DisplayName("[VALIDATION] Deve jogar uma exception ao receber letras para a idade")
    public void shouldThrowExceptionAgeNotANumber() {
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> ageValidation.validate(createAuthorAgeNotANumber));

        assertEquals(exception.getMessage(), "A idade do Autor deve ser um número.");
    }
}
