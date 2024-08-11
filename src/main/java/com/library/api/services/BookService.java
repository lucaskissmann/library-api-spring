package com.library.api.services;

import org.springframework.stereotype.Service;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.helpers.exceptions.NotFoundException;
import com.library.api.modules.books.Book;
import com.library.api.modules.books.dtos.BookRequestDTO;
import com.library.api.modules.books.dtos.BookResponseDTO;
import com.library.api.modules.books.mappers.BookMapper;
import com.library.api.repositories.AuthorRepository;
import com.library.api.repositories.BookRepository;


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

        for( Long authorId : bookRequestDTO.getAuthorIds() ) {
            authorRepository.findById(authorId)
                    .orElseThrow( () -> new NotFoundException("Autor não encontrado para o ID: #" + authorId));
        }

        Book book = bookRepository.save(bookMapper.toEntity(bookRequestDTO));

        return bookMapper.toResponseDTO(book);
    }
}
