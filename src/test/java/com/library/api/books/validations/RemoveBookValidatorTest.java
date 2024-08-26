package com.library.api.books.validations;

import com.library.api.books.stubs.BookStub;
import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.modules.books.Book;
import com.library.api.modules.books.validations.RemoveBookValidator;
import com.library.api.modules.rentals.Rental;
import com.library.api.rentals.stubs.RentalStub;
import com.library.api.repositories.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class RemoveBookValidatorTest {

    Book bookStub = BookStub.createBookStub();
    Rental rentalStub = RentalStub.createRentalStub();

    @Mock
    private RentalRepository rentalRepository;

    @InjectMocks
    private RemoveBookValidator removeBookValidator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("[VALIDATION] Deve lançar uma exceção ao tentar remover um livro com um aluguel ativo")
    public void shouldThrowBadRequestExceptionWhenBookIsAssociatedWithActiveRental() {
        when(rentalRepository.findByBooksContainsAndIsReturned(bookStub, false)).thenReturn(List.of(rentalStub));

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> removeBookValidator.validate(bookStub));

        assertEquals("Não foi possível remover o livro '" + bookStub.getTitle() + "' pois no momento ele está vinculado a um aluguel ativo",
                exception.getMessage());
    }

    @Test
    @DisplayName("[VALIDATION] Deve permitir a deleção de um livro")
    public void shouldNotThrowExceptionWhenBookIsNotAssociatedWithActiveRental() {
        when(rentalRepository.findByBooksContainsAndIsReturned(bookStub, false)).thenReturn(Collections.emptyList());

        removeBookValidator.validate(bookStub);
    }

}
