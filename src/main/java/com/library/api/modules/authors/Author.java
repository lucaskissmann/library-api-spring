package com.library.api.modules.authors;

import com.library.api.modules.common.entities.Person;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.library.api.modules.books.Book;
import lombok.experimental.SuperBuilder;

@Entity(name = "authors")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Author extends Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;

	private String age;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
		name = "book_mappings",
		joinColumns = {@JoinColumn(name = "ref_author", referencedColumnName = "id")},
		inverseJoinColumns = {@JoinColumn(name = "ref_book", referencedColumnName = "id")}
	)
	private List<Book> books = new ArrayList<>();

	public void addBook(Book book) {
		if(!this.books.contains(book)) {
			this.books.add(book);
		}
	}

	public void removeBook(Book book) {
		this.books.remove(book);
	}
}
