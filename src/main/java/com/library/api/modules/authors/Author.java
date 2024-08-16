package com.library.api.modules.authors;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.library.api.modules.books.Book;

@Entity(name = "authors")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;

	private String name;

	private String idade;

	private String genero;

	@ManyToMany
	@JoinTable(
		name = "book_mappings",
		joinColumns = {@JoinColumn(name = "ref_author", referencedColumnName = "id")},
		inverseJoinColumns = {@JoinColumn(name = "ref_book", referencedColumnName = "id")}
	)
	private List<Book> books = new ArrayList<>();

	public void addBook(Book book) {
		this.books.add(book);
	}

	public void removeBook(Book book) {
		this.books.remove(book);
	}
}
