package com.library.api.modules.authors.validations;

import com.library.api.modules.authors.dtos.AuthorRequestDTO;
import com.library.api.modules.authors.dtos.UpdateAuthorDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthorValidationDTO { ;
    private Long id;
    private String name;
    private String age;
    private String gender;
    private String cpf;

    public AuthorValidationDTO(AuthorRequestDTO dto) {
        this.id = null;
        this.name = dto.getName();
        this.age = dto.getAge();
        this.gender = dto.getGender();
        this.cpf = dto.getCpf();
    }

    public AuthorValidationDTO(UpdateAuthorDTO dto, Long id) {
        this.id = id;
        this.name = dto.getName();
        this.age = dto.getAge();
        this.gender = null;
        this.cpf = dto.getCpf();
    }
}
