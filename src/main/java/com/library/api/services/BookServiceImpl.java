package com.library.api.services;

import com.library.api.modules.authors.Author;
import com.library.api.modules.books.specifications.BookSpecification;
import com.library.api.modules.books.dtos.UpdateBookDTO;
import com.library.api.modules.books.enums.BookCategory;
import com.library.api.modules.books.enums.BookState;
import com.library.api.modules.books.validations.RemoveBookValidator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.helpers.exceptions.NotFoundException;
import com.library.api.modules.books.Book;
import com.library.api.modules.books.dtos.BookRequestDTO;
import com.library.api.modules.books.dtos.BookResponseDTO;
import com.library.api.modules.books.mappers.BookMapper;
import com.library.api.repositories.BookRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorServiceImpl authorService;
    private final RemoveBookValidator removeBookValidator;
    private final BookMapper bookMapper = BookMapper.INSTANCE;

    private BookServiceImpl(BookRepository bookRepository,
                            AuthorServiceImpl authorService,
                            RemoveBookValidator removeBookValidator
    ) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.removeBookValidator = removeBookValidator;
    }

    @Override
    public BookResponseDTO create(BookRequestDTO bookRequestDTO) {
        if (bookRepository.findByTitle(bookRequestDTO.getTitle()).isPresent()) {
            throw new BadRequestException("Livro já cadastrado para o título '" + bookRequestDTO.getTitle() + "'");
        }

        if(!BookCategory.isValid(bookRequestDTO.getCategory())) {
            throw new BadRequestException("A categoria do livro deve ser uma das seguintes opções: 'FICÇÃO', 'ROMANCE', 'TERROR', 'CIÊNCIA', 'INFANTIL'");
        }

        List<Author> authors = authorService.getAuthorsByIds(bookRequestDTO.getAuthorIds());

        Book book = bookMapper.toEntity(bookRequestDTO);
        book.setAuthors(authors);

        authorService.updateAuthorsList(authors, book);

        bookRepository.save(book);

        return bookMapper.toResponseDTO(book);
    }

    @Override
    public BookResponseDTO getBook(Long id) {
        return bookMapper.toResponseDTO(getBookById(id));
    }

    @Override
    public List<BookResponseDTO> getBooks(Long authorId, String title, String category, String state) {
        Specification<Book> spec = BookSpecification.filter(authorId, title, category, state);

        List<Book> books = bookRepository.findAll(spec);

        return bookMapper.toResponseDTOs(books);
    }

    private Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado para o ID: #" + id));
    }

    @Override
    public BookResponseDTO updateBook(Long id, UpdateBookDTO updateDTO) {
        Book book = getBookById(id);

        if(!BookCategory.isValid(updateDTO.getCategory())) {
            throw new BadRequestException("A categoria do livro deve ser uma das seguintes opções: 'FICÇÃO', 'ROMANCE', 'TERROR', 'CIÊNCIA', 'INFANTIL'");
        }

        bookMapper.updateEntityFromDto(book, updateDTO);
        updateBookAuthors(updateDTO.getAuthorIds(), book);
        authorService.updateAuthorsBooks(updateDTO.getAuthorIds(), book);

        this.bookRepository.save(book);

        return bookMapper.toResponseDTO(book);
    }

    private void updateBookAuthors(List<Long> authorIds, Book book) {
        if(authorIds != null) {
            book.setAuthors(authorService.getAuthorsByIds(authorIds));
        }
    }

    @Override
    public void deleteBook(Long id) {
        Book book = getBookById(id);

        removeBookValidator.validate(book);

        for (Author author : book.getAuthors()) {
            author.removeBook(book);
        }

        bookRepository.delete(book);
    }

    @Override
    public List<Book> getBooksByIds(List<Long> bookIds) {
        return bookIds.stream()
                    .map(this::getBookById)
                    .collect(Collectors.toList());
    }

    @Override
    public void returnBooks(List<Book> books) {
        books.forEach(book -> book.setState(BookState.AVAILABLE));

        bookRepository.saveAll(books);
    }

    @Override
    public void rentBooks(List<Book> books) {
        books.forEach(book -> book.setState(BookState.UNAVAILABLE));

        bookRepository.saveAll(books);
    }

    @Override
    public void validateAvailabilityOfBooks(List<Book> books) {
        books.forEach(b -> {
            if(!b.getState().equals(BookState.AVAILABLE)) {
                throw new BadRequestException("O livro de ID '" + b.getId() + "' não está disponível para locação");
            }
        });
    }
}
