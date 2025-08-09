package com.library.api.v2.infra.books.repositories;

import com.library.api.v2.domain.authors.Author;
import com.library.api.v2.infra.books.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookEntityRepository extends JpaRepository<BookEntity, UUID> {
    List<BookEntity> findByAuthorsContains(Author author);

}
