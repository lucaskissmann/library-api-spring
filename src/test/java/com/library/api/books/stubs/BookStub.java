package com.library.api.books.stubs;


import com.library.api.authors.stubs.AuthorStub;
import com.library.api.modules.books.Book;
import com.library.api.modules.books.dtos.BookRequestDTO;
import com.library.api.modules.books.enums.BookState;

import java.util.List;

public interface BookStub {

    static Book createBookStub() {
        return Book.builder()
                .id(1L)
                .title("1984")
                .state(BookState.AVAILABLE)
                .authors(List.of(AuthorStub.createAuthorStub()))
                .build();
    }

    static BookRequestDTO createInvalidBookRequestDTO() {
        return BookRequestDTO.builder()
                .title(null)
                .state(null)
                .publicationDate(null)
                .authorIds(null)
                .build();
    }
}
