package com.library.api.v2.infra.authors.repositories;

import com.library.api.v2.application.authors.repositories.AuthorRepository;
import com.library.api.v2.domain.authors.Author;
import com.library.api.v2.infra.authors.mappers.AuthorEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {

    private final AuthorEntityRepository authorEntityRepository;
    AuthorEntityMapper mapper = AuthorEntityMapper.INSTANCE;

    @Override
    public Optional<Author> findAuthorByName(String authorName) {
        return authorEntityRepository.findAuthorByName(authorName)
                .map(mapper::toDomain);
    }

    @Override
    public List<Author> findByNameContaining(String authorName) {
        return authorEntityRepository.findByNameContaining(authorName)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Author> findByCpf(String cpf) {
        return authorEntityRepository.findByCpf(cpf)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Author> findById(UUID id) {
        return authorEntityRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public void delete(Author author) {
        authorEntityRepository.delete(mapper.toEntity(author));
    }

    @Override
    public void save(Author author) {
        authorEntityRepository.save(mapper.toEntity(author));
    }
}
