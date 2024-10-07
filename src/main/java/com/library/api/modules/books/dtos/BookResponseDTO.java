package com.library.api.modules.books.dtos;

import com.library.api.modules.books.enums.BookCategory;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

import com.library.api.modules.authors.dtos.AuthorResponseDTO;
import com.library.api.modules.books.enums.BookState;

@Getter
@Builder
public class BookResponseDTO {
    Long id;
    String title;
    String publicationDate;
    String isbn;
    BookState state;
    BookCategory category;
    List<AuthorResponseDTO> authors;
}
