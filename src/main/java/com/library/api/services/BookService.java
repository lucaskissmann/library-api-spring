package com.library.api.services;

import com.library.api.modules.authors.Author;
import org.springframework.stereotype.Service;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.helpers.exceptions.NotFoundException;
import com.library.api.modules.books.Book;
import com.library.api.modules.books.dtos.BookRequestDTO;
import com.library.api.modules.books.dtos.BookResponseDTO;
import com.library.api.modules.books.mappers.BookMapper;
import com.library.api.repositories.AuthorRepository;
import com.library.api.repositories.BookRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper = BookMapper.INSTANCE;

    private BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public BookResponseDTO create(BookRequestDTO bookRequestDTO) {
        if (bookRepository.findByTitle(bookRequestDTO.getTitle()).isPresent()) {
            throw new BadRequestException("Livro já cadastrado para o título '" + bookRequestDTO.getTitle() + "'");
        }

        List<Author> authors = getAuthorsByIds(bookRequestDTO.getAuthorIds());

        Book book = bookMapper.toEntity(bookRequestDTO);
        book.setAuthors(authors);

        updateAuthorsList(authors, book);

        bookRepository.save(book);

        return bookMapper.toResponseDTO(book);
    }

    private void updateAuthorsList(List<Author> authors, Book book) {
        for(Author author : authors) {
            author.addBook(book);
        }
    }

    private List<Author> getAuthorsByIds(List<Long> ids) {
        return ids.stream()
                .map(id -> authorRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Autor não encontrado para o ID: #" + id)))
                .collect(Collectors.toList());
    }

    public BookResponseDTO getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado para o ID: #" + id));

        return bookMapper.toResponseDTO(book);
    }

    public List<BookResponseDTO> getBooks() {
        return bookMapper.toResponseDTOs(bookRepository.findAll());
    }
}
