package com.library.api.authors.stubs;

import com.library.api.modules.authors.validations.AuthorValidationDTO;

public interface AuthorValidationStub {

    static AuthorValidationDTO createAuthorValidationDTO() {
        AuthorValidationDTO validationDTO = new AuthorValidationDTO();
            validationDTO.setId(1L);
            validationDTO.setName("Lucas");
            validationDTO.setAge("23");
            validationDTO.setGender("masculino");

        return validationDTO;
    }

    static AuthorValidationDTO createAuthorValidationDTONotUniqueName() {
        AuthorValidationDTO validationDTO = new AuthorValidationDTO();
        validationDTO.setId(2L);
        validationDTO.setName("Lucas");
        validationDTO.setAge("24");
        validationDTO.setGender("masculino");

        return validationDTO;
    }

    static AuthorValidationDTO createAuthorValidationDTOInvalidAge() {
        AuthorValidationDTO validationDTO = new AuthorValidationDTO();
        validationDTO.setId(1L);
        validationDTO.setName("Lucas");
        validationDTO.setAge("17");
        validationDTO.setGender("masculino");

        return validationDTO;
    }

    static AuthorValidationDTO createAuthorValidationDTOAgeNotANumber() {
        AuthorValidationDTO validationDTO = new AuthorValidationDTO();
        validationDTO.setId(1L);
        validationDTO.setName("Lucas");
        validationDTO.setAge("abc");
        validationDTO.setGender("masculino");

        return validationDTO;
    }

    static AuthorValidationDTO createAuthorValidationDTOInvalidGender() {
        AuthorValidationDTO validationDTO = new AuthorValidationDTO();
        validationDTO.setId(1L);
        validationDTO.setName("Lucas");
        validationDTO.setAge("23");
        validationDTO.setGender("genero");

        return validationDTO;
    }
}
