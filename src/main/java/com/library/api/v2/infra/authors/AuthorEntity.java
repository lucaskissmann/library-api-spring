package com.library.api.v2.infra.authors;

import com.library.api.v2.infra.books.BookEntity;
import com.library.api.v2.infra.commons.PersonEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.List;
import java.util.UUID;

@Entity(name = "authors")
public class AuthorEntity extends PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID )
    private UUID id;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "book_mappings",
            joinColumns = {@JoinColumn(name = "ref_author", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "ref_book", referencedColumnName = "id")}
    )
    private List<BookEntity> books;
}
