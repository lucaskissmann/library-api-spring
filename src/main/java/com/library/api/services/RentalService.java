package com.library.api.services;

import com.library.api.helpers.exceptions.NotFoundException;
import com.library.api.modules.books.Book;
import com.library.api.modules.books.enums.BookState;
import com.library.api.modules.rentals.Rental;
import com.library.api.modules.rentals.dtos.RentalRequestDTO;
import com.library.api.modules.rentals.dtos.RentalResponseDTO;
import com.library.api.modules.rentals.mappers.RentalMapper;
import com.library.api.repositories.BookRepository;
import com.library.api.repositories.RentalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper = RentalMapper.INSTANCE;
    private final BookService bookService;
    private final RenterService renterService;

    public RentalService(RentalRepository rentalRepository, BookService bookService, BookRepository bookRepository, RenterService renterService) {
        this.rentalRepository = rentalRepository;
        this.bookService = bookService;
        this.renterService = renterService;
    }

    public RentalResponseDTO create(RentalRequestDTO dto) {
        List<Book> books = bookService.getBooksByIds(dto.getBookIds());
        Rental rental = rentalMapper.toEntity(dto);

        rental.setRenter(renterService.getRenterById(dto.getRenterId()));
        rental.setBooks(books);

        bookService.rentBooks(books);

        rentalRepository.save(rental);

        return rentalMapper.toResponseDto(rental);
    }

    public List<RentalResponseDTO> getRentals() {
        return rentalMapper.toResponseDto(rentalRepository.findAll());
    }

    public RentalResponseDTO getRental(Long id) {
        return rentalMapper.toResponseDto(getRentalById(id));
    }

    private Rental getRentalById(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aluguel não encontrado para o ID: #" + id));
    }

    public RentalResponseDTO returnBooks(Long id) {
        Rental rental = getRentalById(id);

        bookService.returnBooks(rental.getBooks());

        rental.setIsReturned(true);
        rentalRepository.save(rental);

        return rentalMapper.toResponseDto(rental);
    }


}
