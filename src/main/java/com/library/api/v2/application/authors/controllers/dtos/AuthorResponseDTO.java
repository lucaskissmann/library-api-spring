package com.library.api.v2.application.authors.controllers.dtos;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class AuthorResponseDTO {
	UUID id;
	String name;
	String age;
	String cpf;
}
