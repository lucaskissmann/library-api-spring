package com.library.api.v2.infra.books.repositories;

import com.library.api.v2.application.books.repositories.BookRepository;
import com.library.api.v2.domain.authors.Author;
import com.library.api.v2.domain.books.Book;
import com.library.api.v2.infra.authors.mappers.AuthorEntityMapper;
import com.library.api.v2.infra.books.mappers.BookEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {

    private final BookEntityRepository bookEntityRepository;
    private final AuthorEntityMapper authorMapper = AuthorEntityMapper.INSTANCE;
    private final BookEntityMapper bookMapper = BookEntityMapper.INSTANCE;

    @Override
    public List<Book> findByAuthorsContains(Author author) {
        return bookEntityRepository.findByAuthorsContains(authorMapper.toEntity(author))
                .stream().map(bookMapper::toDomain)
                .toList();
    }
}
