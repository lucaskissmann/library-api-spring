package com.library.api.v2.application.authors.facades;

import com.library.api.v2.application.authors.usecases.RemoveAuthorUseCase;
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
//    private final LogService logService;

    @Transactional
    public void removeAuthor(UUID id) {
        removeAuthorUseCase.execute(id);
        log.info("Autor de id {} removido com sucesso!", id);
    }
}
