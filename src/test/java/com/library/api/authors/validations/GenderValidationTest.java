package com.library.api.authors.validations;

import com.library.api.authors.stubs.AuthorStub;
import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.modules.authors.Author;
import com.library.api.modules.authors.validations.AgeValidation;
import com.library.api.modules.authors.validations.GenderValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
public class GenderValidationTest {
    private Author createAuthorStub;
    private Author createAuthorInvalidGender;

    private final GenderValidation genderValidation = new GenderValidation();

    @BeforeEach
    public void setUp() {
        createAuthorStub = AuthorStub.createAuthorStub();
        createAuthorInvalidGender = AuthorStub.createAuthorInvalidGender();
    }

    @Test
    @DisplayName("[VALIDATION] Não deve lançar nenhuma exception para um gênero válido.")
    public void shouldNotThrowExceptionWhenGenderIsValid() {
        genderValidation.validate(createAuthorStub);
    }

    @Test
    @DisplayName("[VALIDATION] Deve lançar exception para um gênero não válido")
    public void shouldThrowExceptionWhenGenderIsInvalid() {
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> genderValidation.validate(createAuthorInvalidGender));

        assertEquals(exception.getMessage(), "O gênero do Autor deve ser 'MASCULINO', 'FEMININO' ou 'OUTROS'");
    }
}
