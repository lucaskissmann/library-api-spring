package com.library.api.v2.infra.authors.repositories;

import com.library.api.v2.infra.authors.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorEntityRepository extends JpaRepository<AuthorEntity, UUID> {
    Optional<AuthorEntity> findAuthorByName(String authorName);
    List<AuthorEntity> findByNameContaining(String authorName);

    Optional<AuthorEntity> findByCpf(String cpf);
}