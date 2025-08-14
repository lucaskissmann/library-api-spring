package com.library.api.v2.infra.books.mappers;

import com.library.api.v2.domain.books.Book;
import com.library.api.v2.infra.books.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookEntityMapper {

    BookEntityMapper INSTANCE = Mappers.getMapper(BookEntityMapper.class);

    BookEntity toBookEntity(Book book);
    Book toDomain(BookEntity entity);
}
