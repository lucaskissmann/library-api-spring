package com.library.api.repositories;

import com.library.api.modules.authors.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import com.library.api.modules.books.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
    List<Book> findByAuthorsContains(Author author);
}
