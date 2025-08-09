package com.library.api.v2.domain.authors;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.v2.domain.authors.enums.Genders;
import com.library.api.v2.domain.books.Book;
import com.library.api.v2.domain.commons.entities.Name;
import com.library.api.v2.domain.commons.entities.Person;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

import static com.library.api.helpers.constants.MessageConstants.INVALID_AGE;

@Getter
@Setter
public class Author extends Person {
    private UUID id;
    private List<Book> books;

    public Author(Name name, Genders gender, String cpf, LocalDate birthdate) {
        super(name, gender, cpf, birthdate);
        validateAge();
        this.id = UUID.randomUUID();
    }

    private void validateAge() {
        int age = Period.between(getBirthdate(), LocalDate.now()).getYears();
        if (age < 18 || age > 120) {
            throw new BadRequestException(INVALID_AGE);
        }
    }

    public void addBook(Book book) {
        if(!this.books.contains(book)) {
            this.books.add(book);
        }
    }

    public void removeBook(Book book) {
        this.books.remove(book);
    }
}
