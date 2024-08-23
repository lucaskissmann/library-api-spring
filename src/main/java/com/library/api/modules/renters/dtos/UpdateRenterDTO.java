package com.library.api.modules.renters.dtos;

import com.library.api.helpers.validations.CPF;
import com.library.api.helpers.validations.ValidDate;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateRenterDTO {
    private String name;
    private String gender;
    private String phone;

    @Email(message = "O email está em formato inválido.")
    private String email;

    @ValidDate
    private String birthDate;

    @CPF
    private String cpf;
}
