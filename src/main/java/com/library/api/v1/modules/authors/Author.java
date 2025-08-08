package com.library.api.v1.modules.authors;

import com.library.api.v1.modules.books.Book;
import com.library.api.v1.modules.common.entities.Person;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

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
