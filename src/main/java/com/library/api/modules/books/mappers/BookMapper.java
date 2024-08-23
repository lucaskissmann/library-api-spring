package com.library.api.modules.books.mappers;

import com.library.api.modules.books.dtos.UpdateBookDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import com.library.api.modules.books.Book;
import com.library.api.modules.books.dtos.BookRequestDTO;
import com.library.api.modules.books.dtos.BookResponseDTO;

import java.util.List;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "state", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "rentals", ignore = true)
    @Mapping(target = "isbn", source = "dto.isbn", qualifiedByName = "cleanISBN")
    Book toEntity(BookRequestDTO dto);

    BookResponseDTO toResponseDTO(Book book);

    List<BookResponseDTO> toResponseDTOs(List<Book> books);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "rentals", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "isbn", source = "updateDTO.isbn", qualifiedByName = "cleanISBN")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@MappingTarget Book book, UpdateBookDTO updateDTO);

    @Named("cleanISBN")
    default String cleanISBN(String isbn) {
        if (isbn != null && !isbn.isEmpty()) {
            return isbn.replaceAll("\\D", "");
        }
        return null;
    }
}
