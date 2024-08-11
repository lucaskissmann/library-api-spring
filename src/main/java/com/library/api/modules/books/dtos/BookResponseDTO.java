package com.library.api.modules.books.dtos;

import lombok.Getter;

import java.util.List;

import com.library.api.modules.authors.dtos.AuthorResponseDTO;
import com.library.api.modules.books.enums.BookState;

@Getter
public class BookResponseDTO {
    Long id;
    String title;
    String publicationDate;
    List<AuthorResponseDTO> authors;
    BookState state;
}
