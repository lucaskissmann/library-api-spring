package com.library.api.modules.books.validations;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.modules.books.Book;
import com.library.api.repositories.RentalRepository;
import org.springframework.stereotype.Component;

@Component
public class RemoveBookValidator {

    private final RentalRepository rentalRepository;

    public RemoveBookValidator(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void validate(Book book) {
        if(!rentalRepository.findByBooksContainsAndIsReturned(book, false).isEmpty()) {
            throw new BadRequestException("Não foi possível remover o livro '" + book.getTitle() + "' pois no momento ele está vinculado a um aluguel ativo");
        }
    }
}
