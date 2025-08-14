package com.library.api.v2.application.authors.mappers;

import com.library.api.helpers.utils.LibraryUtils;

import com.library.api.v2.application.authors.controllers.dtos.AuthorRequestDTO;
import com.library.api.v2.application.authors.controllers.dtos.AuthorResponseDTO;
import com.library.api.v2.application.authors.controllers.dtos.UpdateAuthorDTO;
import com.library.api.v2.domain.authors.Author;
import com.library.api.v2.domain.authors.enums.Genders;
import com.library.api.v2.domain.commons.entities.Name;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorDTOMapper {

    AuthorDTOMapper INSTANCE = Mappers.getMapper(AuthorDTOMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    @Mapping(target = "name", source = "dto.name", qualifiedByName = "stringToName")
    @Mapping(target = "cpf", source = "dto.cpf", qualifiedByName = "cleanCpf")
    Author toDomain(AuthorRequestDTO dto);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "gender", ignore = true)
//    @Mapping(target = "books", ignore = true)
//    @Mapping(target = "cpf", source = "dto.cpf", qualifiedByName = "cleanCpf")
//    @Mapping(target = "name", source = "dto.name", qualifiedByName = "stringToName")
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void updateEntityFromDto(@MappingTarget Author domain, UpdateAuthorDTO dto);

    @Mapping(source = "name", target = "name", qualifiedByName = "nameToString")
    AuthorResponseDTO toResponseDTO(Author domain);

    @Named("cleanCpf")
    default String cleanCpf(String cpf) {
        if (cpf != null && !cpf.isEmpty()) {
            return cpf.replaceAll("\\D", "");
        }
        return null;
    }

    default Genders mapGender(String gender) {
        if (gender == null) {
            return null;
        }
        return Enum.valueOf(Genders.class, gender.toUpperCase());
    }

    @Named("stringToName")
    default Name stringToName(String fullName) {
        return LibraryUtils.stringToName(fullName);
    }

    @Named("nameToString")
    default String nameToString(Name name) {
        return LibraryUtils.nameToString(name);
    }

}
