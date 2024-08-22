package com.library.api.modules.authors.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthorRequestDTO {
	
	@NotBlank(message = "O nome do Autor deve ser informado.")
	String name;

	@NotBlank(message = "A idade do Autor deve ser informada.")
	String age;

	@NotBlank(message = "O gênero do Autor deve ser informado.")
	String gender;

}
