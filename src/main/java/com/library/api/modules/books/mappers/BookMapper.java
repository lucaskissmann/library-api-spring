package com.library.api.modules.books.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.library.api.modules.books.Book;
import com.library.api.modules.books.dtos.BookRequestDTO;
import com.library.api.modules.books.dtos.BookResponseDTO;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book toEntity(BookRequestDTO dto);

    BookResponseDTO toResponseDTO(Book book);
}
