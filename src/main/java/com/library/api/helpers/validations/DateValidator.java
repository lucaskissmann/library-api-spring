package com.library.api.helpers.validations;

import com.library.api.context.ApplicationContext;
import com.library.api.helpers.exceptions.BadRequestException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator implements ConstraintValidator<ValidDate, String> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {
        if (date == null || date.isEmpty()) {
            return true;
        }

        try {
            LocalDate formattedDate = LocalDate.parse(date, FORMATTER);

            if (ApplicationContext.today().isBefore(formattedDate)) {
                throw new BadRequestException("A data de publicação do livro deve ser anterior ao dia atual.");
            }

            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
