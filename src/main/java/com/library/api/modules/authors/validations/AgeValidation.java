package com.library.api.modules.authors.validations;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.modules.authors.Author;
import org.springframework.stereotype.Component;

@Component
public class AgeValidation implements AuthorValidator {

    @Override
    public void validate(Author author) {
        try {
            int age = Integer.parseInt(author.getIdade());
            if (age < 18 || age > 120) {
                throw new BadRequestException("A idade do Autor deve estar entre 18 e 120.");
            }
        } catch (NumberFormatException e) {
            throw new BadRequestException("A idade do Autor deve ser um número.");
        }
    }
}
