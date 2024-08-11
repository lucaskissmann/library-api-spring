package com.library.api.authors.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import com.library.api.authors.stubs.AuthorStub;
import com.library.api.helpers.exceptions.NotFoundException;
import com.library.api.modules.authors.Author;
import com.library.api.modules.authors.dtos.AuthorRequestDTO;
import com.library.api.modules.authors.dtos.AuthorResponseDTO;
import com.library.api.modules.authors.mappers.AuthorMapper;
import com.library.api.repositories.AuthorRepository;
import com.library.api.services.AuthorService;

import java.util.Optional;
import java.util.Set;

@ActiveProfiles("test")
public class AuthorServiceTest {

	Author authorStub = AuthorStub.createAuthorStub();
	AuthorRequestDTO invalidAuthorRequestDTO = AuthorStub.createInvalidAuthorRequestDTO();
	AuthorRequestDTO authorRequestStub = AuthorStub.createAuthorRequestDTO();
	AuthorResponseDTO authorResponseStub = AuthorStub.createAuthorResponseDTO();

	private Validator validator;

	@Mock
	private AuthorRepository authorRepository;

	@Mock
	private AuthorMapper authorMapper;

	@InjectMocks
	private AuthorService authorService;


	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	@DisplayName("[SERVICE] Deve criar um autor")
	public void shouldCreateValidAuthor() {
		//Arrange + Act
		when(this.authorMapper.toEntity(authorRequestStub)).thenReturn(authorStub);
		when(this.authorMapper.toResponseDTO(authorStub)).thenReturn(authorResponseStub);
		AuthorResponseDTO responseDTO = authorService.create(authorRequestStub);

		//Assert
		assertEquals(authorRequestStub.getName(), responseDTO.getName());
		assertEquals(authorRequestStub.getIdade(), responseDTO.getIdade());
		assertEquals(authorRequestStub.getGenero(), authorStub.getGenero());
		verify(authorRepository, times(1)).save(any(Author.class));
	}

	@Test
	@DisplayName("[SERVICE] Não deve criar um autor com dados inválidos")
	public void shouldNotCreateAuthor() {
		Set<ConstraintViolation<AuthorRequestDTO>> violations = validator.validate(invalidAuthorRequestDTO);

		assertFalse(violations.isEmpty());
	}

	@Test
	@DisplayName("[SERVICE] Não deve encontrar um autor para o ID informado")
	public void shouldNotFindAuthorById() {

		NotFoundException exception = assertThrows(NotFoundException.class,
				() -> authorService.getAuthor(1L));

		assertEquals("Não foi localizado nenhum autor com o id: #" + 1L, exception.getMessage());
	}

	@Test
	@DisplayName("[SERVICE] Deve encontrar um autor para o ID informado")
	public void shouldFindAuthorById() {
		when(authorRepository.findById(authorStub.getId())).thenReturn(Optional.of(authorStub));
		when(authorMapper.toResponseDTO(authorStub)).thenReturn(authorResponseStub);

		AuthorResponseDTO foundAuthor = authorService.getAuthor(authorStub.getId());

		assertNotNull(foundAuthor);
		assertEquals(authorStub.getId(), foundAuthor.getId());
		assertEquals(authorStub.getName(), foundAuthor.getName());
	}
	
}
