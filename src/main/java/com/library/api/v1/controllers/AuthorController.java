package com.library.api.v1.controllers;


import com.library.api.context.ApplicationContext;
import com.library.api.v1.modules.authors.dtos.AuthorRequestDTO;
import com.library.api.v1.modules.authors.dtos.AuthorResponseDTO;
import com.library.api.v1.modules.authors.dtos.UpdateAuthorDTO;
import com.library.api.v1.services.AuthorServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping(path = ApplicationContext.VERSION + "/authors")
public class AuthorController
	extends
		Controller{
	
	private final AuthorServiceImpl authorService;

	public AuthorController(AuthorServiceImpl authorService) {
		this.authorService = authorService;
	}

	@PostMapping()
	public ResponseEntity<AuthorResponseDTO> create(@Valid @RequestBody AuthorRequestDTO content) {
		AuthorResponseDTO createdAuthor = authorService.create(content);
		return created(createdAuthor);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AuthorResponseDTO> update(@Valid @RequestBody UpdateAuthorDTO updateDto, @PathVariable Long id) {
		AuthorResponseDTO updatedAuthor = authorService.update(updateDto, id);
		return ok(updatedAuthor);
  	}

	@GetMapping
	public ResponseEntity<List<AuthorResponseDTO>> getAuthors(@RequestParam(required = false) String name) {
		return ok(authorService.getAuthors(name));
	}

	@GetMapping("/{id}")
	public ResponseEntity<AuthorResponseDTO> getAuthor(@PathVariable Long id) {
		return ok(authorService.getAuthor(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		authorService.deleteAuthor(id);
		return noContent();
	}
}
