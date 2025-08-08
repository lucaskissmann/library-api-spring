package com.library.api.v1.modules.books.dtos;

import com.library.api.helpers.validations.ValidDate;
import com.library.api.v1.modules.books.validations.ISBN;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UpdateBookDTO {
    private String title;

    @ValidDate
    private String publicationDate;

    private List<Long> authorIds;

    @ISBN
    private String isbn;

    private String category;
}
