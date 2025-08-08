package com.library.api.v1.modules.authors.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthorResponseDTO {
	Long id;
	String name;
	String age;
	String cpf;
}
