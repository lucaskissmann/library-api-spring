package com.library.api.v2.application.authors.controllers.dtos;

import com.library.api.helpers.validations.CPF;
import com.library.api.helpers.validations.ValidDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class AuthorRequestDTO {
	
	@NotBlank(message = "O nome do Autor deve ser informado.")
	String name;

	@NotBlank(message = "A data de nascimento do Autor deve ser informada.")
	@ValidDate
	String birthdate;

	@NotBlank(message = "O gênero do Autor deve ser informado.")
	String gender;

	@NotBlank(message = "O CPF do Autor deve ser informado")
	@CPF
	String cpf;

}
