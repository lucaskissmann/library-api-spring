package com.library.api.modules.books.dtos;

import lombok.Getter;

import java.util.List;

import com.library.api.modules.authors.dtos.AuthorResponseDTO;
import com.library.api.modules.books.enums.BookState;
import lombok.Setter;

@Getter
@Setter
public class BookResponseDTO {
    Long id;
    String title;
    String publicationDate;
    String isbn;
    BookState state;
    List<AuthorResponseDTO> authors;
}
