package com.library.api.authors.stubs;

import com.library.api.modules.authors.Author;
import com.library.api.modules.authors.dtos.AuthorRequestDTO;
import com.library.api.modules.authors.dtos.AuthorResponseDTO;
import com.library.api.modules.authors.dtos.UpdateAuthorDTO;

public interface AuthorStub {
	
	static Author createAuthorStub() {
		return Author.builder()
		    	.id(1L)
				.name("Lucas")
				.idade("23")
				.genero("Masculino")
				.build();
	}

	static Author createAuthorTooOld() {
		return Author.builder()
				.id(1L)
				.name("Lucas")
				.idade("121")
				.genero("Masculino")
				.build();
	}

	static Author createAuthorTooYoung() {
		return Author.builder()
				.id(1L)
				.name("Lucas")
				.idade("17")
				.genero("Masculino")
				.build();
	}

	static Author createAuthorAgeNotANumber() {
		return Author.builder()
				.id(1L)
				.name("Lucas")
				.idade("abc")
				.genero("Masculino")
				.build();
	}

	static Author createAuthorInvalidGender() {
		return Author.builder()
				.id(1L)
				.name("Lucas")
				.idade("23")
				.genero("Masculinos")
				.build();
	}

	static Author createAuthorNotUniqueName() {
		return Author.builder()
				.id(2L)
				.name("Lucas")
				.idade("24")
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

	static UpdateAuthorDTO updateAuthorDTO() {
		return UpdateAuthorDTO.builder()
				.name("Lucas Updated")
				.idade("24")
				.build();
	}
}
