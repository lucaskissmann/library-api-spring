package com.library.api.v2.application.books.repositories;

import com.library.api.v2.domain.authors.Author;
import com.library.api.v2.domain.books.Book;

import java.util.List;

public interface BookRepository {
    List<Book> findByAuthorsContains(Author author);
}
