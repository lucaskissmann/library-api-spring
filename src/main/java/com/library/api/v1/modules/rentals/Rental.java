package com.library.api.v1.modules.rentals;

import com.library.api.v1.modules.books.Book;
import com.library.api.v1.modules.renters.Renter;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
