package com.library.api.modules.authors.validations;

import com.library.api.modules.authors.Author;

public interface AuthorValidator<AuthorValidationDTO> {
    void validate(AuthorValidationDTO dto);
}
