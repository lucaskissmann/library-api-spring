package com.library.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.api.modules.authors.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findAuthorByName(String authorName);
}
