package com.library.api.modules.books.mappers;

import com.library.api.modules.authors.Author;
import com.library.api.modules.authors.dtos.AuthorResponseDTO;
import com.library.api.modules.books.dtos.UpdateBookDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import com.library.api.modules.books.Book;
import com.library.api.modules.books.dtos.BookRequestDTO;
import com.library.api.modules.books.dtos.BookResponseDTO;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book toEntity(BookRequestDTO dto);

    @Mapping(target = "authors", ignore = true)
    BookResponseDTO toResponseDTO(Book book);

    @AfterMapping
    default void mapAuthorsToAuthorResponseDTOs(@MappingTarget BookResponseDTO bookResponseDTO, Book book) {
        if (book.getAuthors() != null) {
            List<AuthorResponseDTO> authorResponseDTOs = new ArrayList<>();
            for (Author author : book.getAuthors()) {
                authorResponseDTOs.add(
                        AuthorResponseDTO.builder()
                                .id(author.getId())
                                .name(author.getName())
                                .idade(author.getIdade())
                                .build());
            }

            bookResponseDTO.setAuthors(authorResponseDTOs);
        }
    }

    List<BookResponseDTO> toResponseDTOs(List<Book> books);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@MappingTarget Book book, UpdateBookDTO updateDTO);
}
