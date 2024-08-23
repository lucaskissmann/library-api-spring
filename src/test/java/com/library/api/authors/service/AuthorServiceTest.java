package com.library.api.authors.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.library.api.books.stubs.BookStub;
import com.library.api.modules.authors.dtos.UpdateAuthorDTO;
import com.library.api.modules.authors.validations.AuthorValidationDTO;
import com.library.api.modules.authors.validations.AuthorValidator;
import com.library.api.modules.authors.validations.RemoveAuthorValidator;
import com.library.api.modules.books.Book;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.library.api.authors.stubs.AuthorStub;
import com.library.api.helpers.exceptions.NotFoundException;
import com.library.api.modules.authors.Author;
import com.library.api.modules.authors.dtos.AuthorRequestDTO;
import com.library.api.modules.authors.dtos.AuthorResponseDTO;
import com.library.api.repositories.AuthorRepository;
import com.library.api.services.AuthorServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@DisplayName("AuthorService")
public class AuthorServiceTest {

	Author authorStub = AuthorStub.createAuthorStub();
	AuthorRequestDTO invalidAuthorRequestDTO = AuthorStub.createInvalidAuthorRequestDTO();
	AuthorRequestDTO authorRequestStub = AuthorStub.createAuthorRequestDTO();
	AuthorResponseDTO authorResponseStub = AuthorStub.createAuthorResponseDTO();
	UpdateAuthorDTO updateAuthorDTO = AuthorStub.updateAuthorDTO();

	private Validator validator;

	@Mock
	private AuthorRepository authorRepository;

	@Mock
	private List<AuthorValidator<AuthorValidationDTO>> validators;

	@Mock
	private RemoveAuthorValidator removeAuthorValidator;

	@InjectMocks
	private AuthorServiceImpl authorService;


	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		validator = Validation.buildDefaultValidatorFactory().getValidator();

		AuthorValidator mockValidator = mock(AuthorValidator.class);
		doNothing().when(mockValidator).validate(any(Author.class));
		validators.add(mockValidator);
	}

	@Test
	@DisplayName("[SERVICE] Deve criar um autor")
	public void shouldCreateValidAuthor() {
		//Arrange + Act
		AuthorResponseDTO responseDTO = authorService.create(authorRequestStub);

		//Assert
		assertEquals(authorRequestStub.getName(), responseDTO.getName());
		assertEquals(authorRequestStub.getAge(), responseDTO.getAge());
		assertEquals(authorRequestStub.getGender(), authorStub.getGender().toString());
		verify(authorRepository, times(1)).save(any(Author.class));
	}

	@Test
	@DisplayName("[SERVICE] Não deve criar um autor com dados inválidos")
	public void shouldNotCreateAuthor() {
		Set<ConstraintViolation<AuthorRequestDTO>> violations = validator.validate(invalidAuthorRequestDTO);

		assertFalse(violations.isEmpty());
		verify(authorRepository, times(0)).save(any(Author.class));

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

		AuthorResponseDTO foundAuthor = authorService.getAuthor(authorStub.getId());

		assertNotNull(foundAuthor);
		assertEquals(authorStub.getId(), foundAuthor.getId());
		assertEquals(authorStub.getName(), foundAuthor.getName());
	}

	@Test
	@DisplayName("[SERVICE] Deve atualizar um autor para o ID informado")
	public void shouldUpdateAuthor() {
		Long authorId = 1L;

		when(authorRepository.findById(authorStub.getId())).thenReturn(Optional.of(authorStub));
		when(authorRepository.save(authorStub)).thenReturn(authorStub);

		AuthorResponseDTO result = authorService.update(updateAuthorDTO, authorId);

		assertNotNull(result);
		assertEquals(result.getName(), updateAuthorDTO.getName());
		assertEquals(result.getAge(), updateAuthorDTO.getAge());
		assertEquals(result.getId(), authorStub.getId());

		verify(authorRepository).findById(authorId);
		verify(authorRepository).save(authorStub);
	}

	@Test
	@DisplayName("[SERVICE] Não deve atualizar um autor para o ID incorreto")
	public void shouldNotUpdateAuthor() {
		Long authorId = 2L;

		NotFoundException exception = assertThrows(NotFoundException.class,
				() -> authorService.update(updateAuthorDTO, authorId));

		assertEquals("Não foi localizado nenhum autor com o id: #" + authorId, exception.getMessage());
	}

	@Test
	@DisplayName("[SERVICE] Deve retornar uma lista de autores quando o filtro de nome é null")
	public void shouldFindAllAuthorsWhenNameIsNull() {
		List<Author> authorsStub = List.of(authorStub);

		when(authorRepository.findAll()).thenReturn(authorsStub);

		List<AuthorResponseDTO> authors = authorService.getAuthors(null);

		assertNotNull(authors);
		assertEquals(authorsStub.size(), authors.size());
		assertEquals(authorsStub.get(0).getName(), authors.get(0).getName());
	}

	@Test
	@DisplayName("[SERVICE] Deve retornar uma lista de autores filtrado pelo nome")
	public void shouldFindAllAuthorsWhenNameIsNotNull() {
		List<Author> authorsStub = List.of(authorStub);

		when(authorRepository.findByNameContaining(authorStub.getName())).thenReturn(authorsStub);

		List<AuthorResponseDTO> authors = authorService.getAuthors(authorStub.getName());

		assertNotNull(authors);
		assertEquals(authorsStub.size(), authors.size());
		assertEquals(authorsStub.get(0).getName(), authors.get(0).getName());

		verify(authorRepository, times(1)).findByNameContaining(authorStub.getName());
	}

	@Test
	@DisplayName("[SERVICE] Deve retornar uma lista vazia de autores filtrado por um nome inexistente")
	public void shouldReturnEmptyListWhenNoAuthorsFoundByName() {
		List<AuthorResponseDTO> authors = authorService.getAuthors(authorStub.getName());

		assertTrue(authors.isEmpty());
		verify(authorRepository, times(1)).findByNameContaining(authorStub.getName());
	}

	@Test
	@DisplayName("[SERVICE] Deve retornar os autores correspondentes aos IDs fornecidos")
	public void shouldReturnAuthorsByIds() {
		when(authorRepository.findById(authorStub.getId())).thenReturn(Optional.of(authorStub));

		List<Author> result = authorService.getAuthorsByIds(List.of(authorStub.getId()));

		assertEquals(1, result.size());
		assertTrue(result.contains(authorStub));

		verify(authorRepository, times(1)).findById(authorStub.getId());
	}

	@Test
	@DisplayName("[SERVICE] Deve remover um autor pelo ID")
	public void shouldDeleteAuthorById() {
		when(authorRepository.findById(anyLong())).thenReturn(Optional.of(authorStub));
		doNothing().when(removeAuthorValidator).validate(any(Author.class));

		authorService.deleteAuthor(1L);

		verify(authorRepository, times(1)).delete(any(Author.class));
	}

	@Test
	@DisplayName("[SERVICE] Não deve remover um autor para ID inexistente")
	public void shouldNotDeleteAuthor() {
		NotFoundException exception = assertThrows(NotFoundException.class, () -> authorService.deleteAuthor(1L));

		assertEquals("Não foi localizado nenhum autor com o id: #1", exception.getMessage());
		verify(authorRepository, times(0)).delete(any(Author.class));
	}

	@Test
	@DisplayName("[SERVICE] Deve adicionar o livro na lista dos autores")
	public void shouldUpdateAuthorsList() {
		Book book = BookStub.createBookStub();

		authorService.updateAuthorsList(List.of(authorStub), BookStub.createBookStub());

		assertEquals(1, book.getAuthors().size());
		assertEquals(authorStub.getName(), book.getAuthors().get(0).getName());
	}
	
}
