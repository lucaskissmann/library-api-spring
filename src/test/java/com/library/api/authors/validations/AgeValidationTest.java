package com.library.api.authors.validations;

import com.library.api.authors.stubs.AuthorStub;
import com.library.api.authors.stubs.AuthorValidationStub;
import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.modules.authors.Author;
import com.library.api.modules.authors.validations.AgeValidation;
import com.library.api.modules.authors.validations.AuthorValidationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AgeValidationTest {

    private AuthorValidationDTO createAuthorValidationStub;
    private AuthorValidationDTO createAuthorValidationStubInvalidAge;
    private AuthorValidationDTO createAuthorValidationStubAgeNotANumber;

    private final AgeValidation ageValidation = new AgeValidation();

    @BeforeEach
    public void setUp() {
        createAuthorValidationStub = AuthorValidationStub.createAuthorValidationDTO();
        createAuthorValidationStubInvalidAge = AuthorValidationStub.createAuthorValidationDTOInvalidAge();
        createAuthorValidationStubAgeNotANumber = AuthorValidationStub.createAuthorValidationDTOAgeNotANumber();
    }

    @Test
    @DisplayName("[VALIDATION] Não deve lançar nenhuma exception para uma idade válida.")
    public void shouldNotThrowExceptionWhenAgeIsValid() {
        ageValidation.validate(createAuthorValidationStub);
    }

    @Test
    @DisplayName("[VALIDATION] Deve jogar uma exception ao receber uma idade fora do range aceito")
    public void shouldThrowExceptionAuthorTooOld() {
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> ageValidation.validate(createAuthorValidationStubInvalidAge));

        assertEquals(exception.getMessage(), "A idade do Autor deve estar entre 18 e 120.");
    }

    @Test
    @DisplayName("[VALIDATION] Deve jogar uma exception ao receber letras para a idade")
    public void shouldThrowExceptionAgeNotANumber() {
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> ageValidation.validate(createAuthorValidationStubAgeNotANumber));

        assertEquals(exception.getMessage(), "A idade do Autor deve ser um número.");
    }
}
