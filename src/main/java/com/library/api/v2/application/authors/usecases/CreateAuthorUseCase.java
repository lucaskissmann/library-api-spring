package com.library.api.v2.application.authors.usecases;

import com.library.api.v2.application.authors.controllers.dtos.AuthorRequestDTO;
import com.library.api.v2.application.authors.controllers.dtos.AuthorResponseDTO;
import com.library.api.v2.application.authors.mappers.AuthorDTOMapper;
import com.library.api.v2.application.authors.repositories.AuthorRepository;
import com.library.api.v2.domain.authors.Author;
import com.library.api.v2.domain.authors.enums.Genders;
import com.library.api.v2.domain.authors.validations.UniqueCpfValidation;
import com.library.api.v2.domain.authors.validations.UniqueNameValidation;
import com.library.api.v2.domain.commons.entities.Name;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class CreateAuthorUseCase {
    private final AuthorRepository repository;
    private final UniqueNameValidation nameValidation;
    private final UniqueCpfValidation cpfValidation;
    private final AuthorDTOMapper mapper = AuthorDTOMapper.INSTANCE;

    public AuthorResponseDTO execute(AuthorRequestDTO dto) {
        nameValidation.validateForCreate(dto.getName());
        cpfValidation.validateForCreate(dto.getCpf());

        Author author = Author.createNew(
                new Name(dto.getName()),
                Genders.fromString(dto.getGender()),
                dto.getCpf(),
                LocalDate.parse(dto.getBirthdate())
        );

        repository.save(author);

        return mapper.toResponseDTO(author);
    }
}
