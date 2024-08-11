package com.library.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.api.modules.books.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
}
