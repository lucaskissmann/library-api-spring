package com.library.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

	@Autowired
	private AuthorRepository authorRepository;

	private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

	public AuthorResponseDTO create(AuthorRequestDTO dto) {
		Author author = authorMapper.toEntity(dto);

		authorRepository.save(author);

		return authorMapper.toResponseDTO(author);
	}

	public AuthorResponseDTO update(UpdateAuthorDTO updateDto, Long authorId) {
		Author author = authorRepository.findById(authorId)
				.orElseThrow( () -> new RuntimeException("Não foi encontado um Autor com o id: #" + authorId ));

		authorMapper.updateEntityFromDto(updateDto, author);

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
