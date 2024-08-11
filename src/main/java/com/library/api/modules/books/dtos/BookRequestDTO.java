package com.library.api.modules.books.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

import com.library.api.modules.books.enums.BookState;

@Getter
//@Builder
public class BookRequestDTO {

    @NotBlank(message = "O título do livro deve ser informado")
    String title;

    @NotBlank(message = "A data de publicação do livro deve ser informada no formato 'YYYY-MM-DD'")
    String publicationDate;

    @NotNull(message = "A lista de IDs de autores não pode ser nula")
    @NotEmpty(message = "Deve ser informado ao menos um ID de autor")
    List<Long> authorIds;

    BookState state = BookState.AVAILABLE;
}
