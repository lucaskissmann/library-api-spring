package com.library.api.v2.application.authors.controllers.dtos;

import com.library.api.helpers.validations.CPF;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateAuthorDTO {
	String name;
	String age;
	@CPF
	String cpf;
}
