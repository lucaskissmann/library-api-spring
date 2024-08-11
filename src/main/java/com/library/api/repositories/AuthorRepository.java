package com.library.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.api.modules.authors.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
	
}
