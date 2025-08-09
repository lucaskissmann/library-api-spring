package com.library.api.v2.application.authors.usecases;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.helpers.exceptions.NotFoundException;
import com.library.api.v2.application.authors.repositories.AuthorRepository;
import com.library.api.v2.application.books.repositories.BookRepository;
import com.library.api.v2.domain.authors.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.library.api.helpers.constants.MessageConstants.CANT_REMOVE_AUTHOR;
import static com.library.api.helpers.constants.MessageConstants.NOT_FOUND_AUTHOR;

@RequiredArgsConstructor
@Service
public class RemoveAuthorUseCase {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public void execute(UUID id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_AUTHOR));

        if (!bookRepository.findByAuthorsContains(author).isEmpty())
            throw new BadRequestException(String.format(CANT_REMOVE_AUTHOR, author.getName()));

        authorRepository.delete(author);
    }
}
