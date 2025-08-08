package com.library.api.v1.modules.authors.validations;

public interface AuthorValidator<AuthorValidationDTO> {
    void validate(AuthorValidationDTO dto);
}
