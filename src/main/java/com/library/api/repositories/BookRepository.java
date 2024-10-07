package com.library.api.repositories;

import com.library.api.modules.authors.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import com.library.api.modules.books.Book;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    Optional<Book> findByTitle(String title);
    List<Book> findByAuthorsContains(Author author);

    @Query("SELECT b FROM books b WHERE " +
            "(:author IS NULL OR :author MEMBER OF b.authors) AND " +
            "(:title IS NULL OR b.title LIKE %:title%)")
    List<Book> findByAuthorAndTitle(@Param("author") Author author, @Param("title") String title);
}
