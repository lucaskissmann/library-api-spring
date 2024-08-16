package com.library.api.services;

import com.library.api.modules.authors.validations.AuthorValidator;
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

@Service
public class AuthorService {

	private final AuthorRepository authorRepository;

	private final List<AuthorValidator> validators;

	private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

	public AuthorService(AuthorRepository authorRepository, List<AuthorValidator> validators) {
		this.authorRepository = authorRepository;
		this.validators = validators;
	}

	public AuthorResponseDTO create(AuthorRequestDTO dto) {
		Author author = authorMapper.toEntity(dto);
		validators.forEach(validator -> validator.validate(author));

		authorRepository.save(author);

		return authorMapper.toResponseDTO(author);
	}

	public AuthorResponseDTO update(UpdateAuthorDTO updateDto, Long authorId) {
		Author author = authorRepository.findById(authorId)
				.orElseThrow( () -> new NotFoundException("Não foi localizado nenhum autor com o id: #" + authorId ));

		authorMapper.updateEntityFromDto(updateDto, author);

		validators.forEach(validator -> validator.validate(author));

		authorRepository.save(author);

		return authorMapper.toResponseDTO(author);
	}

	public List<AuthorResponseDTO> getAuthors() {
		return authorMapper.toResponseDto(authorRepository.findAll());
	}

	public AuthorResponseDTO getAuthor(Long authorId) {
		Author author = authorRepository.findById(authorId)
				.orElseThrow(() -> new NotFoundException("Não foi localizado nenhum autor com o id: #" + authorId));

		return authorMapper.toResponseDTO(author);
	}
}
