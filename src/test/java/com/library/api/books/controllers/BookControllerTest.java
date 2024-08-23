package com.library.api.books.controllers;

import com.library.api.books.stubs.BookStub;
import com.library.api.modules.books.dtos.BookRequestDTO;
import com.library.api.modules.books.dtos.BookResponseDTO;
import com.library.api.modules.books.dtos.UpdateBookDTO;
import com.library.api.helpers.QueryProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.library.api.helpers.Serializer.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String PATH = "/books";

    private final BookRequestDTO mockBookDTO = BookStub.createBookRequestDTO();

    @Test
    @DisplayName("[POST] Deve retornar 201 ao criar um Livro")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertAuthors),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB),
    })
    public void shouldReturn201_CreateBook() throws Exception {
        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(mockBookDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value(mockBookDTO.getTitle()))
                .andExpect(jsonPath("$.isbn").value(mockBookDTO.getIsbn()))
                .andExpect(jsonPath("$.authors[0].id").value(mockBookDTO.getAuthorIds().get(0)));
    }

    @Test
    @DisplayName("[GET] Deve retornar 200 ao buscar um Livro pelo ID informado")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertBooks),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB),
    })
    public void shouldReturn200_GetBookById() throws Exception {
        final BookResponseDTO mockBookResponseDTO = BookStub.createBookResponseDTO();
        mockMvc.perform(get(PATH + "/{bookId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value(mockBookResponseDTO.getTitle()))
                .andExpect(jsonPath("$.isbn").value(mockBookResponseDTO.getIsbn()))
                .andExpect(jsonPath("$.authors").isArray());
    }

    @Test
    @DisplayName("[GET] Deve retornar 404 ao buscar um Livro para ID inexistente")
    public void shouldReturn404_GetBookById() throws Exception {
        mockMvc.perform(get(PATH + "/{bookId}", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Livro não encontrado para o ID: #1"));
    }

    @Test
    @DisplayName("[PUT] Deve retornar 200 ao atualizar um Livro para o ID informado")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertAuthors),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertBooks),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertBookMappings),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB),
    })
    public void shouldReturn200_UpdateBookById() throws Exception {
        final UpdateBookDTO mockUpdateBookDTO = BookStub.updateBookDTO();

        mockMvc.perform(put(PATH + "/{bookId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(mockUpdateBookDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value(mockUpdateBookDTO.getTitle()))
                .andExpect(jsonPath("$.isbn").value(mockUpdateBookDTO.getIsbn()));
    }

    @Test
    @DisplayName("[PUT] Deve retornar 404 ao tentar atualizar um Livro com ID inexistente")
    public void shouldReturn404_UpdateBookById() throws Exception {
        final UpdateBookDTO mockUpdateBookDTO = BookStub.updateBookDTO();
        mockMvc.perform(put(PATH + "/{bookId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(mockUpdateBookDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Livro não encontrado para o ID: #1"));
    }

    @Test
    @DisplayName("[DELETE] Deve retornar 204 ao deletar um Livro pelo ID informado")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertBooks),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB),
    })
    public void shouldReturn204_DeleteBookById() throws Exception {
        mockMvc.perform(delete(PATH + "/{bookId}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("[DELETE] Deve retornar 404 ao tentar deletar um Livro com ID inexistente")
    public void shouldReturn404_DeleteBookById() throws Exception {
        mockMvc.perform(delete(PATH + "/{bookId}", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Livro não encontrado para o ID: #1"));
    }

    @Test
    @DisplayName("[GET] Deve retornar 200 ao buscar livros por título ou autor")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertAuthors),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertBooks),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertBookMappings),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB),
    })
    public void shouldReturn200_GetBooksByTitleOrAuthor() throws Exception {
        final List<BookResponseDTO> mockBookResponseDTOs = List.of(BookStub.createBookResponseDTO());

        mockMvc.perform(get(PATH)
                        .param("title", "1984")
                        .param("authorId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(mockBookResponseDTOs.get(0).getId()))
                .andExpect(jsonPath("$[0].title").value(mockBookResponseDTOs.get(0).getTitle()))
                .andExpect(jsonPath("$[0].isbn").value(mockBookResponseDTOs.get(0).getIsbn()))
                .andExpect(jsonPath("$[0].authors").isArray());
    }
}

