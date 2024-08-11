package com.library.api.authors.stubs;

import com.library.api.modules.authors.Author;
import com.library.api.modules.authors.dtos.AuthorRequestDTO;
import com.library.api.modules.authors.dtos.AuthorResponseDTO;

public interface AuthorStub {
	
	static Author createAuthorStub() {
		return Author.builder()
		    	.id(1L)
				.name("Lucas")
				.idade("23")
				.genero("Masculino")
				.build();
	}

	static AuthorRequestDTO createInvalidAuthorRequestDTO() {
		return AuthorRequestDTO.builder()
				.name(null)
				.idade(null)
				.genero(null)
				.build();
	}

	static AuthorRequestDTO createAuthorRequestDTO() {
		return AuthorRequestDTO.builder()
				.name("Lucas")
				.idade("23")
				.genero("Masculino")
				.build();
	}

	static AuthorResponseDTO createAuthorResponseDTO() {
		return AuthorResponseDTO.builder()
				.id(1L)
				.name("Lucas")
				.idade("23")
				.build();
	}



}
