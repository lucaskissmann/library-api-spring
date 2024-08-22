package com.library.api.modules.books.dtos;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

import com.library.api.modules.authors.dtos.AuthorResponseDTO;
import com.library.api.modules.books.enums.BookState;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookResponseDTO {
    Long id;
    String title;
    String publicationDate;
    String isbn;
    BookState state;
    List<AuthorResponseDTO> authors;
}
