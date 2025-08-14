package com.library.api.v2.application.authors.controllers;

import com.library.api.context.ApplicationContext;
import com.library.api.v2.application.Controller;
import com.library.api.v2.application.authors.controllers.dtos.AuthorRequestDTO;
import com.library.api.v2.application.authors.controllers.dtos.AuthorResponseDTO;
import com.library.api.v2.application.authors.controllers.dtos.UpdateAuthorDTO;
import com.library.api.v2.application.authors.facades.AuthorFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApplicationContext.VERSION + "/authors")
public class AuthorController extends Controller implements AuthorSwagger {

    private final AuthorFacade facade;

    @PostMapping()
    public ResponseEntity<AuthorResponseDTO> create(@Valid AuthorRequestDTO dto) {
        return created(facade.createAuthor(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> update(@Valid UpdateAuthorDTO dto, UUID id) {
        return ok(facade.updateAuthor(dto, id));
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> getAuthors(String name) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> getAuthor(UUID id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(UUID id) {
        facade.removeAuthor(id);
        return noContent();
    }
}
