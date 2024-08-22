package com.library.api.rentals.stubs;

import com.library.api.books.stubs.BookStub;
import com.library.api.context.ApplicationContext;
import com.library.api.modules.rentals.Rental;
import com.library.api.renters.stubs.RenterStub;

import java.util.List;

public interface RentalStub {

    static Rental createRentalStub() {
        return Rental.builder()
                .id(1L)
                .books(List.of(BookStub.createBookStub()))
                .renter(RenterStub.createRenterStub())
                .rentalDate(ApplicationContext.today())
                .returnDate(ApplicationContext.today().plusDays(2))
                .build();
    }
}
