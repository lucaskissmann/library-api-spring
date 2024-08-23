package com.library.api.helpers.validations;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class CPFValidatorTest {

    private final CPFValidator validator = new CPFValidator();

    @Test
    void shouldValidateValidCPF() {
        String validCPF = "123.456.789-09";
        boolean isValid = validator.isValid(validCPF, mock(ConstraintValidatorContext.class));
        assertTrue(isValid);
    }

    @Test
    void shouldInvalidateInvalidCPF() {
        String invalidCPF = "123.456.789-00";
        boolean isValid = validator.isValid(invalidCPF, mock(ConstraintValidatorContext.class));
        assertFalse(isValid);
    }

    @Test
    void shouldValidateNullCPF() {
        boolean isValid = validator.isValid(null, mock(ConstraintValidatorContext.class));
        assertTrue(isValid);
    }

    @Test
    void shouldValidateEmptyCPF() {
        boolean isValid = validator.isValid("", mock(ConstraintValidatorContext.class));
        assertTrue(isValid);
    }
}
