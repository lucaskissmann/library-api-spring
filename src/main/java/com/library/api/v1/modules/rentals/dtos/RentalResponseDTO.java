package com.library.api.v1.modules.rentals.dtos;

import com.library.api.v1.modules.books.dtos.BookResponseDTO;
import com.library.api.v1.modules.renters.dtos.RenterResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RentalResponseDTO {
    Long id;
    String rentalDate;
    String returnDate;
    Boolean isReturned;
    RenterResponseDTO renter;
    List<BookResponseDTO> books;
}
