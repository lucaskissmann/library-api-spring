package com.library.api.rentals.service;

import com.library.api.books.stubs.BookStub;
import com.library.api.helpers.exceptions.NotFoundException;
import com.library.api.modules.books.Book;
import com.library.api.modules.rentals.Rental;
import com.library.api.modules.rentals.dtos.RentalRequestDTO;
import com.library.api.modules.rentals.dtos.RentalResponseDTO;
import com.library.api.rentals.stubs.RentalStub;
import com.library.api.repositories.RentalRepository;
import com.library.api.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@DisplayName("RentalService")
public class RentalServiceTest {

    private Rental rentalStub;
    private RentalRequestDTO rentalRequestStub;
    private List<Book> booksStub;
    private RentalResponseDTO rentalResponseStub;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private BookServiceImpl bookService;

    @Mock
    private RenterServiceImpl renterService;

    @InjectMocks
    private RentalServiceImpl rentalService;

    @BeforeEach
    public void setUp() {
        openMocks(this);

        rentalStub = RentalStub.createRentalStub();
        rentalRequestStub = RentalStub.createRentalRequestStub();
        booksStub = List.of(BookStub.createBookStub());
        rentalResponseStub = RentalStub.createRentalResponseStub();
    }

    @Test
    @DisplayName("[SERVICE] Deve criar um aluguel")
    public void shouldCreateARental() {
        when(bookService.getBooksByIds(rentalRequestStub.getBookIds())).thenReturn(booksStub);
        doNothing().when(bookService).validateAvailabilityOfBooks(booksStub);
        when(renterService.getRenterById(rentalRequestStub.getRenterId())).thenReturn(rentalStub.getRenter());
        when(rentalRepository.save(any(Rental.class))).thenReturn(rentalStub);
        doNothing().when(bookService).rentBooks(booksStub);

        RentalResponseDTO response = rentalService.create(rentalRequestStub);

        assertEquals(rentalResponseStub.getIsReturned(), false);
        assertEquals(rentalResponseStub.getRentalDate(), response.getRentalDate());
        assertEquals(rentalResponseStub.getReturnDate(), response.getReturnDate());
        assertEquals(rentalResponseStub.getBooks().size(), response.getBooks().size());
        assertEquals(rentalResponseStub.getRenter().getId(), response.getRenter().getId());

        verify(bookService, times(1)).getBooksByIds(rentalRequestStub.getBookIds());
        verify(bookService, times(1)).validateAvailabilityOfBooks(booksStub);
        verify(renterService, times(1)).getRenterById(rentalRequestStub.getRenterId());
        verify(rentalRepository, times(1)).save(any(Rental.class));
        verify(bookService, times(1)).rentBooks(booksStub);
    }

    @Test
    @DisplayName("[SERVICE] Deve obter um aluguel por ID")
    public void shouldGetRentalById() {
        when(rentalRepository.findById(anyLong())).thenReturn(Optional.of(rentalStub));
        RentalResponseDTO response = rentalService.getRental(rentalStub.getId());

        assertEquals(rentalStub.getId(), response.getId());

        verify(rentalRepository, times(1)).findById(rentalStub.getId());
    }

    @Test
    @DisplayName("[SERVICE] Deve lançar NotFoundException se aluguel não for encontrado")
    public void shouldThrowNotFoundExceptionWhenRentalNotFound() {
        when(rentalRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows( NotFoundException.class,
                () -> rentalService.getRental(rentalStub.getId()));

        assertEquals("Aluguel não encontrado para o ID: #" + rentalStub.getId(), exception.getMessage());

        verify(rentalRepository, times(1)).findById(rentalStub.getId());
    }

    @Test
    @DisplayName("[SERVICE] Deve retornar livros e marcar aluguel como devolvido")
    public void shouldReturnBooksAndMarkAsReturned() {
        when(rentalRepository.findById(anyLong())).thenReturn(Optional.of(rentalStub));
        when(rentalRepository.save(any(Rental.class))).thenReturn(rentalStub);

        RentalResponseDTO response = rentalService.returnBooks(rentalStub.getId());

        assertEquals(rentalStub.getId(), response.getId());
        assertEquals(true, response.getIsReturned());

        verify(bookService, times(1)).returnBooks(rentalStub.getBooks());
        verify(rentalRepository, times(1)).save(rentalStub);
    }
}
