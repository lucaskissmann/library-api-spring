package com.library.api.v2.domain.authors;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.v2.domain.authors.enums.Genders;
import com.library.api.v2.domain.books.Book;
import com.library.api.v2.domain.commons.entities.Name;
import com.library.api.v2.domain.commons.entities.Person;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.library.api.helpers.constants.MessageConstants.INVALID_AGE;
import static com.library.api.helpers.constants.MessageConstants.INVALID_GENDER;

@Getter
public class Author extends Person {
    private UUID id;
    private List<Book> books;

    public Author(UUID id, Name name, Genders gender, String cpf, LocalDate birthdate, List<Book> books) {
        super(name, gender, cpf, birthdate);
        validateAge(birthdate);
//        validateGender(gender);
        this.id = id;
        this.books = new ArrayList<>(books);
    }

    public static Author createNew(Name name, Genders gender, String cpf, LocalDate birthdate) {
        return new Author(UUID.randomUUID(), name, gender, cpf, birthdate, new ArrayList<>());
    }

    private void validateAge(LocalDate birthdate) {
        int age = Period.between(birthdate, LocalDate.now()).getYears();
        if (age < 18 || age > 120)
            throw new BadRequestException(INVALID_AGE);
    }

//    private void validateGender(Genders gender) {
//        if (gender != null && !Genders.isValid(gender.name()))
//            throw new BadRequestException(String.format(INVALID_GENDER, Arrays.toString(Genders.values())));
//    }

    public void updateBirthdate(LocalDate birthdate) {
        validateAge(birthdate);
        setBirthdate(birthdate);
    }

    public void updateGender(String gender) {
        setGender(Genders.fromString(gender));
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
