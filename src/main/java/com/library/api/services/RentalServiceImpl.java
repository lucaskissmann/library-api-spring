package com.library.api.services;

import com.library.api.helpers.exceptions.NotFoundException;
import com.library.api.modules.books.Book;
import com.library.api.modules.books.enums.BookState;
import com.library.api.modules.rentals.Rental;
import com.library.api.modules.rentals.dtos.RentalRequestDTO;
import com.library.api.modules.rentals.dtos.RentalResponseDTO;
import com.library.api.modules.rentals.dtos.ReturnRentalDTO;
import com.library.api.modules.rentals.mappers.RentalMapper;
import com.library.api.repositories.RentalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper = RentalMapper.INSTANCE;
    private final BookServiceImpl bookService;
    private final RenterServiceImpl renterService;

    public RentalServiceImpl(RentalRepository rentalRepository, BookServiceImpl bookService, RenterServiceImpl renterService) {
        this.rentalRepository = rentalRepository;
        this.bookService = bookService;
        this.renterService = renterService;
    }

    @Override
    public RentalResponseDTO create(RentalRequestDTO dto) {
        List<Book> books = bookService.getBooksByIds(dto.getBookIds());
        bookService.validateAvailabilityOfBooks(books);

        Rental rental = rentalMapper.toEntity(dto);

        rental.setIsReturned(false);
        rental.setRenter(renterService.getRenterById(dto.getRenterId()));
        rental.setBooks(books);

        bookService.rentBooks(books);

        rentalRepository.save(rental);

        return rentalMapper.toResponseDto(rental);
    }

    @Override
    public List<RentalResponseDTO> getRentals() {
        return rentalMapper.toResponseDto(rentalRepository.findAll());
    }

    @Override
    public RentalResponseDTO getRental(Long id) {
        return rentalMapper.toResponseDto(getRentalById(id));
    }

    private Rental getRentalById(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aluguel não encontrado para o ID: #" + id));
    }

    @Override
    public RentalResponseDTO returnBooks(Long id, ReturnRentalDTO dto) {
        Rental rental = getRentalById(id);

        List<Book> booksToReturn = rental.getBooks().stream()
                .filter(book -> dto.getBookIds().contains(book.getId()))
                .toList();

        bookService.returnBooks(booksToReturn);

        if( rental.getBooks().stream().allMatch(book -> book.getState().equals(BookState.AVAILABLE))) {
            rental.setIsReturned(true);
        }

        rentalRepository.save(rental);

        return rentalMapper.toResponseDto(rental);
    }


}
