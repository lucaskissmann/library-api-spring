package com.library.api.modules.authors.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;

import com.library.api.modules.authors.Author;
import com.library.api.modules.authors.dtos.AuthorRequestDTO;
import com.library.api.modules.authors.dtos.AuthorResponseDTO;
import com.library.api.modules.authors.dtos.UpdateAuthorDTO;

import java.util.List;

@Mapper
public interface AuthorMapper {
	AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

	@Mapping(target = "id", ignore = true)
	Author toEntity(AuthorRequestDTO dto);
	
	AuthorRequestDTO toDTO(Author entity);

	AuthorResponseDTO toResponseDTO(Author entity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "genero", ignore = true)
	void updateEntityFromDto(UpdateAuthorDTO dto, @MappingTarget Author entity);

	List<AuthorResponseDTO> toResponseDto(List<Author> authors);
}
