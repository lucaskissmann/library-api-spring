package com.library.api.modules.books;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate publicationDate;

    @ManyToMany(mappedBy = "books")
    private List<Author> authors = new ArrayList<>();

    private BookState state = BookState.AVAILABLE;
}
