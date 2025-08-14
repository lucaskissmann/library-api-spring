package com.library.api.v2.application.authors.usecases;

import com.library.api.helpers.exceptions.NotFoundException;
import com.library.api.v2.application.authors.controllers.dtos.AuthorResponseDTO;
import com.library.api.v2.application.authors.controllers.dtos.UpdateAuthorDTO;
import com.library.api.v2.application.authors.mappers.AuthorDTOMapper;
import com.library.api.v2.application.authors.mappers.UpdateAuthorMapper;
import com.library.api.v2.application.authors.repositories.AuthorRepository;
import com.library.api.v2.domain.authors.Author;
import com.library.api.v2.domain.authors.validations.UniqueCpfValidation;
import com.library.api.v2.domain.authors.validations.UniqueNameValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.library.api.helpers.constants.MessageConstants.AUTHOR_NOT_FOUND_FOR_ID;

@RequiredArgsConstructor
@Service
public class UpdateAuthorUseCase {
    private final AuthorRepository repository;
    private final UniqueCpfValidation cpfValidation;
    private final UniqueNameValidation nameValidation;
    private final UpdateAuthorMapper mapper;

    public AuthorResponseDTO execute(UpdateAuthorDTO dto, UUID id) {
        nameValidation.validateForUpdate(id, dto.getName());
        cpfValidation.validateForUpdate(id, dto.getCpf());

        Author author = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(AUTHOR_NOT_FOUND_FOR_ID, id)));

        mapper.updateAuthorFromDto(dto, author);

        repository.save(author);

        return AuthorDTOMapper.INSTANCE.toResponseDTO(author);
    }
}
