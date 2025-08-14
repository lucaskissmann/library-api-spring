package com.library.api.v2.application.authors.repositories;


import com.library.api.v2.domain.authors.Author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository {
    Optional<Author> findAuthorByName(String authorName);
    List<Author> findByNameContaining(String authorName);
    Optional<Author> findByCpf(String cpf);
    Optional<Author> findById(UUID id);
    void delete(Author author);
    void save(Author author);
}