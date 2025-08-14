package com.library.api.v2.application.authors.controllers.dtos;

import com.library.api.helpers.validations.CPF;
import com.library.api.helpers.validations.ValidDate;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateAuthorDTO {
	String name;

	@ValidDate
	String birthdate;

	@CPF
	String cpf;

	String gender;
}
