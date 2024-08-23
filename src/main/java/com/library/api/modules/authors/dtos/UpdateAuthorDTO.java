package com.library.api.modules.authors.dtos;

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
