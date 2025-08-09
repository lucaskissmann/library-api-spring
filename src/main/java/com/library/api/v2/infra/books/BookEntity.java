package com.library.api.v2.infra.books;


//import com.library.api.v1.modules.rentals.Rental;
import com.library.api.v2.domain.books.enums.BookCategory;
import com.library.api.v2.domain.books.enums.BookState;
import com.library.api.v2.infra.authors.AuthorEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity(name = "books")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private LocalDate publicationDate;

    private String isbn;

    @ManyToMany(mappedBy = "books")
    private List<AuthorEntity> authors;

//    @ManyToMany(mappedBy = "books")
//    private List<Rental> rentals;

    @Builder.Default
    private BookState state = BookState.AVAILABLE;

    @Enumerated(EnumType.STRING)
    private BookCategory category;
}
