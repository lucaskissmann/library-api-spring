package com.library.api.modules.authors.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthorResponseDTO {
	Long id;
	String name;
	String age;
}
