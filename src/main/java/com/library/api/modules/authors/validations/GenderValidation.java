package com.library.api.modules.authors.validations;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.modules.authors.Author;
import com.library.api.modules.authors.enums.Genders;
import org.springframework.stereotype.Component;

@Component
public class GenderValidation implements AuthorValidator {

    @Override
    public void validate(Author author) {
        if(!Genders.isValid(author.getGenero().toUpperCase())) {
                throw new BadRequestException("O gênero do Autor deve ser 'MASCULINO', 'FEMININO' ou 'OUTROS'");
        }
    }

}
