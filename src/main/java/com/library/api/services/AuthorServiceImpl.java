package com.library.api.services;

import com.library.api.modules.authors.validations.AuthorValidationDTO;
import com.library.api.modules.authors.validations.AuthorValidator;
import com.library.api.modules.authors.validations.RemoveAuthorValidator;
import com.library.api.modules.books.Book;
import org.springframework.stereotype.Service;

import com.library.api.helpers.exceptions.NotFoundException;
import com.library.api.modules.authors.Author;
import com.library.api.modules.authors.dtos.AuthorRequestDTO;
import com.library.api.modules.authors.dtos.AuthorResponseDTO;
import com.library.api.modules.authors.dtos.UpdateAuthorDTO;
import com.library.api.modules.authors.mappers.AuthorMapper;
import com.library.api.repositories.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

	private final AuthorRepository authorRepository;

	private final List<AuthorValidator<AuthorValidationDTO>> validators;
	private final RemoveAuthorValidator removeAuthorValidator;

	private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

	public AuthorServiceImpl(AuthorRepository authorRepository, List<AuthorValidator<AuthorValidationDTO>> validators, RemoveAuthorValidator removeAuthorValidator) {
		this.authorRepository = authorRepository;
		this.validators = validators;
		this.removeAuthorValidator = removeAuthorValidator;
	}

	@Override
	public AuthorResponseDTO create(AuthorRequestDTO dto) {
		validators.forEach(validator -> validator.validate(new AuthorValidationDTO(dto)));
		Author author = authorMapper.toEntity(dto);

		authorRepository.save(author);
		return authorMapper.toResponseDTO(author);
	}

	@Override
	public AuthorResponseDTO update(UpdateAuthorDTO updateDto, Long authorId) {
		validators.forEach(validator -> validator.validate(new AuthorValidationDTO(updateDto, authorId)));

		Author author = getAuthorById(authorId);

		authorMapper.updateEntityFromDto(author, updateDto);
		authorRepository.save(author);
		return authorMapper.toResponseDTO(author);
	}

	protected Author getAuthorById(Long authorId) {
		return authorRepository.findById(authorId)
				.orElseThrow(() -> new NotFoundException("Não foi localizado nenhum autor com o id: #" + authorId));
	}

	@Override
	public void updateAuthorsList(List<Author> authors, Book book) {
		authors.forEach(author -> {
			author.addBook(book);
		});
	}

	@Override
	public List<AuthorResponseDTO> getAuthors(String name) {
		if(name == null) {
			return authorMapper.toResponseDto(authorRepository.findAll());
		} else {
			return authorMapper.toResponseDto(authorRepository.findByNameContaining(name));
		}
	}

	@Override
	public List<Author> getAuthorsByIds(List<Long> ids) {
		return ids.stream()
				.map(this::getAuthorById)
				.collect(Collectors.toList());
	}

	@Override
	public AuthorResponseDTO getAuthor(Long authorId) {
		return authorMapper.toResponseDTO(getAuthorById(authorId));
	}

	@Override
	public void deleteAuthor(Long authorId) {
		Author author = getAuthorById(authorId);

		removeAuthorValidator.validate(author);

		authorRepository.delete(author);
	}
}
