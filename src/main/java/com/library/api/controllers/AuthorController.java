package com.library.api.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import com.library.api.modules.authors.dtos.AuthorRequestDTO;
import com.library.api.modules.authors.dtos.AuthorResponseDTO;
import com.library.api.modules.authors.dtos.UpdateAuthorDTO;
import com.library.api.services.AuthorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController()
@RequestMapping(value = "/authors")
public class AuthorController
	extends
		Controller{
	
	private final AuthorService authorService;

	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@PostMapping()
	public ResponseEntity<AuthorResponseDTO> create(@Valid @RequestBody AuthorRequestDTO content) {
		AuthorResponseDTO createdAuthor = authorService.create(content);
		return created(createdAuthor);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AuthorResponseDTO> update(@RequestBody UpdateAuthorDTO updateDto, @PathVariable Long id) {
		AuthorResponseDTO updatedAuthor = authorService.update(updateDto, id);
		return ok(updatedAuthor);
  	}

	@GetMapping
	public ResponseEntity<List<AuthorResponseDTO>> getAuthors() {
		return ok(authorService.getAuthors());
	}

	@GetMapping("/{id}")
	public ResponseEntity<AuthorResponseDTO> getAuthor(@PathVariable Long id) {
		return ok(authorService.getAuthor(id));
	}
}
