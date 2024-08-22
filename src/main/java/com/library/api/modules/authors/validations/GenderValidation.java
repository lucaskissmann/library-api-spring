package com.library.api.modules.authors.validations;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.modules.authors.Author;
import com.library.api.modules.authors.enums.Genders;
import org.springframework.stereotype.Component;

@Component
public class GenderValidation implements AuthorValidator<AuthorValidationDTO> {

    @Override
    public void validate(AuthorValidationDTO authorDTO) {
        if(authorDTO.getGender() != null && !Genders.isValid(authorDTO.getGender())) {
                throw new BadRequestException("O gênero do Autor deve ser 'MASCULINO', 'FEMININO' ou 'OUTROS'");
        }
    }

}
