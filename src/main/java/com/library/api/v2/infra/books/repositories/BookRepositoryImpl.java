package com.library.api.v2.infra.books.repositories;

import com.library.api.v2.application.books.repositories.BookRepository;
import com.library.api.v2.domain.authors.Author;
import com.library.api.v2.domain.books.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {

    private final BookEntityRepository bookEntityRepository;

    @Override
    public List<Book> findByAuthorsContains(Author author) {
//        return bookEntityRepository.findByAuthorsContains(author);
        return null;
    }
}
