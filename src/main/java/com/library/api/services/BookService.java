package com.library.api.services;

import com.library.api.modules.authors.Author;
import com.library.api.modules.books.dtos.UpdateBookDTO;
import com.library.api.modules.books.enums.BookState;
import com.library.api.modules.books.validations.RemoveBookValidator;
import org.springframework.stereotype.Service;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.helpers.exceptions.NotFoundException;
import com.library.api.modules.books.Book;
import com.library.api.modules.books.dtos.BookRequestDTO;
import com.library.api.modules.books.dtos.BookResponseDTO;
import com.library.api.modules.books.mappers.BookMapper;
import com.library.api.repositories.BookRepository;

import java.util.List;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final RemoveBookValidator removeBookValidator;
    private final BookMapper bookMapper = BookMapper.INSTANCE;

    private BookService(BookRepository bookRepository,
                        AuthorService authorService,
                        RemoveBookValidator removeBookValidator
    ) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.removeBookValidator = removeBookValidator;
    }

    public BookResponseDTO create(BookRequestDTO bookRequestDTO) {
        if (bookRepository.findByTitle(bookRequestDTO.getTitle()).isPresent()) {
            throw new BadRequestException("Livro já cadastrado para o título '" + bookRequestDTO.getTitle() + "'");
        }

        List<Author> authors = authorService.getAuthorsByIds(bookRequestDTO.getAuthorIds());

        Book book = bookMapper.toEntity(bookRequestDTO);
        book.setAuthors(authors);

        authorService.updateAuthorsList(authors, book);

        bookRepository.save(book);

        return bookMapper.toResponseDTO(book);
    }

    public BookResponseDTO getBook(Long id) {
        return bookMapper.toResponseDTO(getBookById(id));
    }

    public List<BookResponseDTO> getBooks() {
        return bookMapper.toResponseDTOs(bookRepository.findAll());
    }

    private Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado para o ID: #" + id));
    }

//    public List<Book> getBooksByAuthor(Author author) {
//        return bookRepository.findByAuthorsContains(author);
//    }

    public BookResponseDTO updateBook(Long id, UpdateBookDTO updateDTO) {
        Book book = getBookById(id);

        bookMapper.updateEntityFromDto(book, updateDTO);
        this.bookRepository.save(book);

        return bookMapper.toResponseDTO(book);
    }

    public void deleteBook(Long id) {
        Book book = getBookById(id);

        removeBookValidator.validate(book);

        bookRepository.delete(book);
    }

    public List<Book> getBooksByIds(List<Long> bookIds) {
        return bookRepository.findAllById(bookIds);
    }

    public void returnBooks(List<Book> books) {
        books.forEach(book -> book.setState(BookState.AVAILABLE));

        bookRepository.saveAll(books);
    }

    public void rentBooks(List<Book> books) {
        books.forEach(book -> book.setState(BookState.UNAVAILABLE));

        bookRepository.saveAll(books);
    }
}
