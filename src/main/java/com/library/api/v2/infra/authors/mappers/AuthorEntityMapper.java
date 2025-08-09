package com.library.api.v2.infra.authors.mappers;

import com.library.api.helpers.utils.LibraryUtils;
import com.library.api.v2.domain.authors.Author;
import com.library.api.v2.domain.commons.entities.Name;
import com.library.api.v2.infra.authors.AuthorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorEntityMapper {
    AuthorEntityMapper INSTANCE = Mappers.getMapper(AuthorEntityMapper.class);

    @Mapping(source = "name", target = "name", qualifiedByName = "nameToString")
    AuthorEntity toEntity(Author domain);

    @Mapping(source = "name", target = "name", qualifiedByName = "stringToName")
    Author toDomain(AuthorEntity entity);

    @Named("nameToString")
    default String nameToString(Name name) {
        return LibraryUtils.nameToString(name);
    }

    @Named("stringToName")
    default Name stringToName(String name) {
        return LibraryUtils.stringToName(name);
    }
}
