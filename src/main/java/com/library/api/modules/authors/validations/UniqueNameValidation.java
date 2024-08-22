package com.library.api.modules.authors.validations;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.modules.authors.Author;
import com.library.api.repositories.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class UniqueNameValidation implements AuthorValidator<AuthorValidationDTO> {

    private final AuthorRepository authorRepository;

    public UniqueNameValidation(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void validate(AuthorValidationDTO authorDTO) {
        Optional<Author> findAuthor = authorRepository.findAuthorByName(authorDTO.getName());
        if(findAuthor.isPresent() && !Objects.equals(findAuthor.get().getId(), authorDTO.getId())) {
            throw new BadRequestException("Autor já cadastrado para o nome '" + authorDTO.getName() + "'");
        }
    }

}
