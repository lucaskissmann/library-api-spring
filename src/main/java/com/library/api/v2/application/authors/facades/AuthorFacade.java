package com.library.api.v2.application.authors.facades;

import com.library.api.v2.application.authors.controllers.dtos.AuthorRequestDTO;
import com.library.api.v2.application.authors.controllers.dtos.AuthorResponseDTO;
import com.library.api.v2.application.authors.controllers.dtos.UpdateAuthorDTO;
import com.library.api.v2.application.authors.usecases.CreateAuthorUseCase;
import com.library.api.v2.application.authors.usecases.RemoveAuthorUseCase;
import com.library.api.v2.application.authors.usecases.UpdateAuthorUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthorFacade {
    private final RemoveAuthorUseCase removeAuthorUseCase;
    private final CreateAuthorUseCase createAuthorUseCase;
    private final UpdateAuthorUseCase updateAuthorUseCase;
//    private final LogService logService;

    public AuthorResponseDTO createAuthor(AuthorRequestDTO authorRequestDTO) {
        var created = createAuthorUseCase.execute(authorRequestDTO);
        log.info("Autor de id {} criado com sucesso!", created.getId());

        return created;
    }

    public AuthorResponseDTO updateAuthor(UpdateAuthorDTO dto, UUID id) {
        var updated = updateAuthorUseCase.execute(dto, id);
        log.info("Autor de id {} atualizado com sucesso!", updated.getId());

        return updated;
    }

    @Transactional
    public void removeAuthor(UUID id) {
        removeAuthorUseCase.execute(id);
        log.info("Autor de id {} removido com sucesso!", id);
    }
}
