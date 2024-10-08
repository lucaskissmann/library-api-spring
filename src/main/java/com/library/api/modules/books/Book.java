package com.library.api.modules.books;

import com.library.api.modules.books.enums.BookCategory;
import com.library.api.modules.rentals.Rental;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.library.api.modules.authors.Author;
import com.library.api.modules.books.enums.BookState;

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
