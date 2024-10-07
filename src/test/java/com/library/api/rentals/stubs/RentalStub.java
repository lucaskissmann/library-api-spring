package com.library.api.rentals.stubs;

import com.library.api.books.stubs.BookStub;
import com.library.api.context.ApplicationContext;
import com.library.api.modules.rentals.Rental;
import com.library.api.modules.rentals.dtos.RentalRequestDTO;
import com.library.api.modules.rentals.dtos.RentalResponseDTO;
import com.library.api.modules.rentals.dtos.ReturnRentalDTO;
import com.library.api.renters.stubs.RenterStub;

import java.util.List;

public interface RentalStub {

    static Rental createRentalStub() {
        return Rental.builder()
                .id(1L)
                .books(List.of(BookStub.createBookStub()))
                .renter(RenterStub.createRenterStub())
                .rentalDate(ApplicationContext.getInstance().today())
                .returnDate(ApplicationContext.getInstance().today().plusDays(2))
                .build();
    }

    static RentalRequestDTO createRentalRequestStub() {
        return RentalRequestDTO.builder()
                .bookIds(List.of(1L))
                .renterId(1L)
                .rentalDate(ApplicationContext.getInstance().today().toString())
                .returnDate(ApplicationContext.getInstance().today().plusDays(2).toString())
                .build();
    }

    static RentalResponseDTO createRentalResponseStub() {
        return RentalResponseDTO.builder()
                .id(1L)
                .books(List.of(BookStub.createBookResponseDTO()))
                .renter(RenterStub.createRenterResponseDTO())
                .rentalDate(ApplicationContext.getInstance().today().toString())
                .returnDate(ApplicationContext.getInstance().today().plusDays(2).toString())
                .isReturned(false)
                .build();
    }

    static ReturnRentalDTO createReturnRentalStub() {
        return ReturnRentalDTO.builder()
                .bookIds(List.of(1L))
                .build();
    }
}
