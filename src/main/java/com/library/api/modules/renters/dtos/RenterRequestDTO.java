package com.library.api.modules.renters.dtos;

import com.library.api.helpers.validations.CPF;
import com.library.api.helpers.validations.ValidDate;
import com.library.api.modules.authors.enums.Genders;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RenterRequestDTO {
    @NotBlank(message = "O nome do locatário deve ser informado.")
    private String name;

    @NotBlank(message = "O gênero do locatário deve ser informado.")
    private String gender;

    @NotBlank(message = "O telefone do locatário deve ser informado.")
    private String phone;

    @NotBlank(message = "O email do locatário deve ser informado.")
    @Email(message = "O email está em formato inválido.")
    private String email;

    @NotBlank(message = "A data de nascimento do locatário deve ser informada.")
    @ValidDate
    private String birthDate;

    @NotBlank(message = "O cpf do locatário deve ser informado.")
    @CPF
    private String cpf;
}
