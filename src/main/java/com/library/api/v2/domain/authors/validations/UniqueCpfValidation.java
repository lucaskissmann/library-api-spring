package com.library.api.v2.domain.authors.validations;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.helpers.utils.LibraryUtils;
import com.library.api.v2.application.authors.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.library.api.helpers.constants.MessageConstants.CPF_ALREADY_EXISTS;

@RequiredArgsConstructor
@Service
public class UniqueCpfValidation {
    private final AuthorRepository authorRepository;

    public void validateForCreate(String cpf) {
        final String normalizedCpf = LibraryUtils.normalizeCPF(cpf);
        authorRepository.findByCpf(normalizedCpf)
                .ifPresent(existing -> {
                    throw new BadRequestException(String.format(CPF_ALREADY_EXISTS, normalizedCpf));
                });
    }

    public void validateForUpdate(UUID id, String cpf) {
        final String normalizeCpf = LibraryUtils.normalizeCPF(cpf);

        authorRepository.findByCpf(normalizeCpf)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id))
                        throw new BadRequestException(String.format(CPF_ALREADY_EXISTS, normalizeCpf));
                });
    }

}
