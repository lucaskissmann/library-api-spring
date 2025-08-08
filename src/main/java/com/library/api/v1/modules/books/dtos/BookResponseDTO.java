package com.library.api.v1.modules.books.dtos;

import com.library.api.v1.modules.authors.dtos.AuthorResponseDTO;
import com.library.api.v1.modules.books.enums.BookCategory;
import com.library.api.v1.modules.books.enums.BookState;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

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
