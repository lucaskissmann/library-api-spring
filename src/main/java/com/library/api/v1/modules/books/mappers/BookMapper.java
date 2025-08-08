package com.library.api.v1.modules.books.mappers;

import com.library.api.v1.modules.books.Book;
import com.library.api.v1.modules.books.dtos.BookRequestDTO;
import com.library.api.v1.modules.books.dtos.BookResponseDTO;
import com.library.api.v1.modules.books.dtos.UpdateBookDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

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
