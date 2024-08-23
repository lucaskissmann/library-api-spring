package com.library.api.books.stubs;


import com.library.api.authors.stubs.AuthorStub;
import com.library.api.modules.books.Book;
import com.library.api.modules.books.dtos.BookRequestDTO;
import com.library.api.modules.books.dtos.BookResponseDTO;
import com.library.api.modules.books.dtos.UpdateBookDTO;
import com.library.api.modules.books.enums.BookState;

import java.time.LocalDate;
import java.util.List;

public interface BookStub {

    static Book createBookStub() {
        return Book.builder()
                .id(1L)
                .title("1984")
                .isbn("9783161484100")
                .publicationDate(LocalDate.parse("1996-06-08"))
                .state(BookState.AVAILABLE)
                .authors(List.of(AuthorStub.createAuthorStub()))
                .build();
    }

    static Book createBookWithoutStateStub() {
        return Book.builder()
                .id(1L)
                .title("1984")
                .authors(List.of(AuthorStub.createAuthorStub()))
                .build();
    }

    static BookRequestDTO createBookRequestDTO() {
        return BookRequestDTO.builder()
                .title("1984")
                .publicationDate("1996-06-08")
                .authorIds(List.of(1L))
                .isbn("9783161484100")
                .build();
    }

    static BookRequestDTO createInvalidBookRequestDTO() {
        return BookRequestDTO.builder()
                .title(null)
                .publicationDate(null)
                .authorIds(null)
                .build();
    }

    static BookResponseDTO createBookResponseDTO() {
        return BookResponseDTO.builder()
                .id(1L)
                .title("1984")
                .publicationDate("1996-06-08")
                .authors(List.of(AuthorStub.createAuthorResponseDTO()))
                .isbn("9783161484100")
                .state(BookState.AVAILABLE)
                .build();
    }

    static UpdateBookDTO updateBookDTO() {
        return UpdateBookDTO.builder()
                .title("1984 updated")
                .publicationDate("2000-01-01")
                .authorIds(List.of(1L))
                .isbn("9783161484100")
                .build();
    }
}
