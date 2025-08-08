package com.library.api.v1.modules.books;

import com.library.api.v1.modules.authors.Author;
import com.library.api.v1.modules.books.enums.BookCategory;
import com.library.api.v1.modules.books.enums.BookState;
import com.library.api.v1.modules.rentals.Rental;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate publicationDate;

    private String isbn;

    @ManyToMany(mappedBy = "books")
    private List<Author> authors = new ArrayList<>();

    @ManyToMany(mappedBy = "books")
    private List<Rental> rentals = new ArrayList<>();

    @Builder.Default
    private BookState state = BookState.AVAILABLE;

    @Enumerated(EnumType.STRING)
    private BookCategory category;
}
