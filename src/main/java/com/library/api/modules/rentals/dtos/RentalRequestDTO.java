package com.library.api.modules.rentals.dtos;

import com.library.api.context.ApplicationContext;
import com.library.api.helpers.validations.ValidDate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RentalRequestDTO {

    @ValidDate
    @Builder.Default
    String rentalDate = ApplicationContext.getInstance().today().toString();

    @ValidDate
    @Builder.Default
    String returnDate = ApplicationContext.getInstance().today().plusDays(2).toString();

    @NotNull(message = "O ID do locatário deve ser informado")
    Long renterId;

    @NotNull(message = "A lista de IDs de livros não pode ser nula")
    @NotEmpty(message = "Deve ser informado ao menos um ID de livro")
    List<Long> bookIds;
}
