package com.library.api.modules.authors.mappers;

import com.library.api.modules.authors.enums.Genders;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import com.library.api.modules.authors.Author;
import com.library.api.modules.authors.dtos.AuthorRequestDTO;
import com.library.api.modules.authors.dtos.AuthorResponseDTO;
import com.library.api.modules.authors.dtos.UpdateAuthorDTO;

import java.util.List;

@Mapper
public interface AuthorMapper {
	AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "books", ignore = true)
	@Mapping(target = "cpf", source = "dto.cpf", qualifiedByName = "cleanCpf")
	Author toEntity(AuthorRequestDTO dto);

	default Genders mapGender(String gender) {
		if (gender == null) {
			return null;
		}
		return Enum.valueOf(Genders.class, gender.toUpperCase());
	}
	
	AuthorRequestDTO toDTO(Author entity);

	AuthorResponseDTO toResponseDTO(Author entity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "gender", ignore = true)
	@Mapping(target = "books", ignore = true)
	@Mapping(target = "cpf", source = "dto.cpf", qualifiedByName = "cleanCpf")
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateEntityFromDto(@MappingTarget Author entity, UpdateAuthorDTO dto);

	List<AuthorResponseDTO> toResponseDto(List<Author> authors);

	@Named("cleanCpf")
	default String cleanCpf(String cpf) {
		if (cpf != null && !cpf.isEmpty()) {
			return cpf.replaceAll("\\D", "");
		}
		return null;
	}
}
