package com.library.api.v2.application.authors.controllers;

import com.library.api.context.ApplicationContext;
import com.library.api.v2.application.authors.controllers.dtos.AuthorRequestDTO;
import com.library.api.v2.application.authors.controllers.dtos.AuthorResponseDTO;
import com.library.api.v2.application.authors.controllers.dtos.UpdateAuthorDTO;
import com.library.api.v2.application.authors.facades.AuthorFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApplicationContext.VERSION + "/authors")
public class AuthorController extends Controller implements AuthorSwagger {

    private final AuthorFacade facade;

    @Override
    public ResponseEntity<AuthorResponseDTO> create(AuthorRequestDTO content) {

        return null;
    }

    @Override
    public ResponseEntity<AuthorResponseDTO> update(UpdateAuthorDTO updateDto, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<AuthorResponseDTO>> getAuthors(String name) {
        return null;
    }

    @Override
    public ResponseEntity<AuthorResponseDTO> getAuthor(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        facade.removeAuthor(id);
        return noContent();
    }
}
