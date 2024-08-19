package com.library.api.modules.authors.validations;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.modules.authors.Author;
import com.library.api.repositories.BookRepository;
import org.springframework.stereotype.Component;

@Component
public class RemoveAuthorValidator {

    private final BookRepository bookRepository;

    public RemoveAuthorValidator(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void validate(Author author) {
        if(!bookRepository.findByAuthorsContains(author).isEmpty()) {
            throw new BadRequestException("O Autor '" + author.getName() + "' não pode ser removido pois possui livros vinculados a ele");
        }
    }
}
