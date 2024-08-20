package com.library.api.modules.rentals.dtos;

import com.library.api.context.ApplicationContext;
import com.library.api.helpers.validations.ValidDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RentalRequestDTO {

    @ValidDate
    String rentalDate;

    @ValidDate
    String returnDate;

    @NotBlank(message = "O ID do locatário deve ser informado")
    Long renterId;

    @NotNull(message = "A lista de IDs de livros não pode ser nula")
    @NotEmpty(message = "Deve ser informado ao menos um ID de livro")
    List<Long> bookIds;
}
