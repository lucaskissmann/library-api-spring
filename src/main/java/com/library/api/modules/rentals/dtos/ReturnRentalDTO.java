package com.library.api.modules.rentals.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnRentalDTO {

    @NotNull(message = "A lista de livros não pode ser nula")
    @NotEmpty(message = "Deve ser informado ao menos um livro")
    private List<Long> bookIds;
}
