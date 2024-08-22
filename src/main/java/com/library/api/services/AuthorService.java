package com.library.api.services;

import com.library.api.modules.authors.Author;
import com.library.api.modules.authors.dtos.AuthorRequestDTO;
import com.library.api.modules.authors.dtos.AuthorResponseDTO;
import com.library.api.modules.authors.dtos.UpdateAuthorDTO;
import com.library.api.modules.books.Book;

import java.util.List;

public interface AuthorService {
    AuthorResponseDTO create(AuthorRequestDTO dto);
    AuthorResponseDTO update(UpdateAuthorDTO updateDto, Long authorId);
    void updateAuthorsList(List<Author> authors, Book book);
    List<AuthorResponseDTO> getAuthors();
    AuthorResponseDTO getAuthor(Long authorId);
    List<Author> getAuthorsByIds(List<Long> ids);
    void deleteAuthor(Long authorId);

}
