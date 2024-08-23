package com.library.api.modules.authors.validations;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.modules.authors.Author;
import com.library.api.repositories.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class UniqueCpfValidation implements AuthorValidator<AuthorValidationDTO> {

    private final AuthorRepository authorRepository;

    public UniqueCpfValidation(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void validate(AuthorValidationDTO authorDTO) {
        if(authorDTO.getCpf() == null)
            return;

        Optional<Author> findAuthor = authorRepository.findByCpf(authorDTO.getCpf().replaceAll("\\D", ""));
        if(findAuthor.isPresent() && !Objects.equals(findAuthor.get().getId(), authorDTO.getId())) {
            throw new BadRequestException("Já existe um Autor cadastrado para o cpf '" + authorDTO.getCpf() + "'");
        }
    }

}
