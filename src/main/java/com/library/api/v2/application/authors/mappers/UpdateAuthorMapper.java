package com.library.api.v2.application.authors.mappers;

import com.library.api.v2.application.authors.controllers.dtos.UpdateAuthorDTO;
import com.library.api.v2.domain.authors.Author;
import com.library.api.v2.domain.commons.entities.Name;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;

@Mapper
public abstract class UpdateAuthorMapper {
    protected Name stringToName(String fullName) {
        return fullName == null ? null : new Name(fullName);
    }

    @AfterMapping
    public void updateAuthorFromDto(UpdateAuthorDTO dto, @MappingTarget Author author) {
        if (dto.getName() != null) {
            author.setName(stringToName(dto.getName()));
        }
        if (dto.getGender() != null) {
            author.updateGender(dto.getGender());
        }
        if (dto.getBirthdate() != null) {
            author.updateBirthdate(LocalDate.parse(dto.getBirthdate()));
        }
        if (dto.getCpf() != null) {
            author.setCpf(dto.getCpf());
        }
    }
}
