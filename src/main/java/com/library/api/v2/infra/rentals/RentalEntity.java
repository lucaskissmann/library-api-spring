package com.library.api.v2.infra.rentals;

import com.library.api.v2.infra.books.BookEntity;
import com.library.api.v2.infra.renters.RenterEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity(name = "rentals")
public class RentalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate rentalDate;

    private LocalDate returnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_renter", nullable = false)
    private RenterEntity renter;

    private Boolean isReturned;

    @ManyToMany
    @JoinTable(
            name = "rental_mappings",
            joinColumns = {@JoinColumn(name = "ref_rental", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "ref_book", referencedColumnName = "id")}
    )
    private List<BookEntity> books;
}
