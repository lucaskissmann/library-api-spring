package com.library.api.v2.domain.authors.validations;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.v2.application.authors.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.library.api.helpers.constants.MessageConstants.NAME_ALREADY_EXISTS;

@RequiredArgsConstructor
@Service
public class UniqueNameValidation {
    private final AuthorRepository authorRepository;

    public void validateForCreate(String name) {
        authorRepository.findAuthorByName(name)
                .ifPresent(existing -> {
                    throw new BadRequestException(String.format(NAME_ALREADY_EXISTS, name));
                });
    }

    public void validateForUpdate(UUID id, String name) {
        authorRepository.findAuthorByName(name)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id))
                        throw new BadRequestException(String.format(NAME_ALREADY_EXISTS, name));
                });
    }
}
