package com.library.api.authors.stubs;

import com.library.api.modules.authors.Author;
import com.library.api.modules.authors.dtos.AuthorRequestDTO;
import com.library.api.modules.authors.dtos.AuthorResponseDTO;
import com.library.api.modules.authors.dtos.UpdateAuthorDTO;
import com.library.api.modules.authors.enums.Genders;

public interface AuthorStub {
	
	static Author createAuthorStub() {
		return Author.builder()
		    	.id(1L)
				.name("Lucas")
				.age("23")
				.gender(Genders.MASCULINO)
				.cpf("79187561000")
				.build();
	}

	static Author createAuthorNotUniqueName() {
		return Author.builder()
				.id(2L)
				.name("Lucas")
				.age("24")
				.gender(Genders.MASCULINO)
				.cpf("79187561000")
				.build();
	}

	static AuthorRequestDTO createInvalidAuthorRequestDTO() {
		return AuthorRequestDTO.builder()
				.name(null)
				.age(null)
				.gender(null)
				.cpf(null)
				.build();
	}

	static AuthorRequestDTO createAuthorRequestDTO() {
		return AuthorRequestDTO.builder()
				.name("Lucas")
				.age("23")
				.gender("MASCULINO")
				.cpf("79187561000")
				.build();
	}

	static AuthorRequestDTO createAuthorRequestDTONotUniqueName() {
		return AuthorRequestDTO.builder()
				.name("Lucas")
				.age("23")
				.gender("MASCULINO")
				.cpf("48641727060")
				.build();
	}

	static AuthorRequestDTO createAuthorRequestDTOInvalidAge() {
		return AuthorRequestDTO.builder()
				.name("Lucas")
				.age("17")
				.gender("MASCULINO")
				.cpf("79187561000")
				.build();
	}

	static AuthorRequestDTO createAuthorRequestDTOAgeNotANumber() {
		return AuthorRequestDTO.builder()
				.name("Lucas")
				.age("abc")
				.gender("MASCULINO")
				.cpf("79187561000")
				.build();
	}

	static AuthorRequestDTO createAuthorRequestDTOInvalidGender() {
		return AuthorRequestDTO.builder()
				.name("Lucas")
				.age("23")
				.gender("GêneroTeste")
				.cpf("79187561000")
				.build();
	}

	static AuthorResponseDTO createAuthorResponseDTO() {
		return AuthorResponseDTO.builder()
				.id(1L)
				.name("Lucas")
				.age("23")
				.cpf("79187561000")
				.build();
	}

	static UpdateAuthorDTO updateAuthorDTO() {
		return UpdateAuthorDTO.builder()
				.name("Lucas Updated")
				.age("24")
				.cpf("79187561000")
				.build();
	}
}
