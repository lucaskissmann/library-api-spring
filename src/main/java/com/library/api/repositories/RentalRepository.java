package com.library.api.repositories;

import com.library.api.modules.books.Book;
import com.library.api.modules.rentals.Rental;
import com.library.api.modules.renters.Renter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByBooksContainsAndIsReturned(Book book, Boolean isReturned);

    List<Rental> findByRenterAndIsReturned(Renter renter, Boolean isReturned);
}
