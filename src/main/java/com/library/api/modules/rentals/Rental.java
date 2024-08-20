package com.library.api.modules.rentals;

import com.library.api.helpers.validations.ValidDate;
import com.library.api.modules.books.Book;
import com.library.api.modules.renters.Renter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "rentals")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate rentalDate;

    private LocalDate returnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_renter", nullable = false)
    private Renter renter;

    private Boolean isReturned;

    @ManyToMany
    @JoinTable(
        name = "rental_mappings",
        joinColumns = {@JoinColumn(name = "ref_rental", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "ref_book", referencedColumnName = "id")}
    )
    private List<Book> books = new ArrayList<>();
}
