package com.library.api.services;

import com.library.api.modules.authors.validations.AuthorValidator;
import com.library.api.modules.authors.validations.RemoveAuthorValidator;
import com.library.api.modules.books.Book;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AuthorService {

	private final AuthorRepository authorRepository;

	private final List<AuthorValidator> validators;
	private final RemoveAuthorValidator removeAuthorValidator;

	private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

	public AuthorService(AuthorRepository authorRepository, List<AuthorValidator> validators, RemoveAuthorValidator removeAuthorValidator) {
		this.authorRepository = authorRepository;
		this.validators = validators;
		this.removeAuthorValidator = removeAuthorValidator;
	}

	public AuthorResponseDTO create(AuthorRequestDTO dto) {
		Author author = authorMapper.toEntity(dto);
		validators.forEach(validator -> validator.validate(author));

		authorRepository.save(author);

		return authorMapper.toResponseDTO(author);
	}

	public AuthorResponseDTO update(UpdateAuthorDTO updateDto, Long authorId) {
		Author author = getAuthorById(authorId);

		authorMapper.updateEntityFromDto(author, updateDto);

		validators.forEach(validator -> validator.validate(author));

		authorRepository.save(author);

		return authorMapper.toResponseDTO(author);
	}

	private Author getAuthorById(Long authorId) {
		return authorRepository.findById(authorId)
				.orElseThrow(() -> new NotFoundException("Não foi localizado nenhum autor com o id: #" + authorId));
	}

	protected void updateAuthorsList(List<Author> authors, Book book) {
		authors.forEach(author -> {
			author.addBook(book);
		});
	}

	public List<AuthorResponseDTO> getAuthors() {
		return authorMapper.toResponseDto(authorRepository.findAll());
	}

	protected List<Author> getAuthorsByIds(List<Long> ids) {
		return ids.stream()
				.map(this::getAuthorById)
				.collect(Collectors.toList());
	}

	public AuthorResponseDTO getAuthor(Long authorId) {
		return authorMapper.toResponseDTO(getAuthorById(authorId));
	}

	public void deleteAuthor(Long authorId) {
		Author author = getAuthorById(authorId);

		removeAuthorValidator.validate(author);

		authorRepository.delete(author);
	}
}
