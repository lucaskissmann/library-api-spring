package com.library.api.modules.authors.validations;

import com.library.api.modules.authors.Author;
import com.library.api.repositories.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class UniqueNameValidation implements AuthorValidator {

    private final AuthorRepository authorRepository;

    public UniqueNameValidation(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void validate(Author author) {
        Optional<Author> findAuthor = authorRepository.findAuthorByName(author.getName());
        if(findAuthor.isPresent() && !Objects.equals(findAuthor.get().getId(), author.getId())) {
            throw new RuntimeException("Autor já cadastrado para o nome '" + author.getName() + "'");
        }
    }

}
