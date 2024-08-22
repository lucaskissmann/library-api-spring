package com.library.api.authors.controllers;

import com.library.api.authors.stubs.AuthorStub;
import com.library.api.helpers.QueryProvider;
import com.library.api.modules.authors.dtos.AuthorRequestDTO;
import com.library.api.modules.authors.dtos.AuthorResponseDTO;
import com.library.api.modules.authors.dtos.UpdateAuthorDTO;
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

import static com.library.api.helpers.Serializer.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String PATH = "/authors";

    private final AuthorRequestDTO mockAuthorDTO = AuthorStub.createAuthorRequestDTO();

    @Test
    @DisplayName("[POST] Deve retornar 201 ao criar um Autor")
    public void shouldReturn201_CreateAuthor() throws Exception {
        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( json(mockAuthorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(mockAuthorDTO.getName()))
                .andExpect(jsonPath("$.age").value(mockAuthorDTO.getAge()));
    }

    @Test
    @DisplayName("[POST] Deve retornar um 400 ao tentar criar Autor com nome duplicado")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertAuthors),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB),
    })
    public void shouldReturn400_CreateAuthorInvalidName() throws Exception {
        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( json(mockAuthorDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Autor já cadastrado para o nome '" + mockAuthorDTO.getName() + "'"));
    }

    @Test
    @DisplayName("[POST] Deve retornar um 400 ao tentar criar Autor com age fora do range estipulado")
    public void shouldReturn400_CreateAuthorInvalidAgeRange() throws Exception {
        final AuthorRequestDTO mockAuthorInvalidAge = AuthorStub.createAuthorRequestDTOInvalidAge();

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( json(mockAuthorInvalidAge)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("A idade do Autor deve estar entre 18 e 120."));
    }

    @Test
    @DisplayName("[POST] Deve retornar um 400 ao tentar criar Autor com age que não seja um número")
    public void shouldReturn400_CreateAuthorAgeNotANumber() throws Exception {
        final AuthorRequestDTO mockAuthorAgeNotANumber = AuthorStub.createAuthorRequestDTOAgeNotANumber();

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( json(mockAuthorAgeNotANumber)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("A idade do Autor deve ser um número."));
    }

    @Test
    @DisplayName("[POST] Deve retornar um 400 ao tentar criar Autor com age que não seja um número")
    public void shouldReturn400_CreateAuthorInvalidGender() throws Exception {
        final AuthorRequestDTO mockAuthorAgeNotANumber = AuthorStub.createAuthorRequestDTOInvalidGender();

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( json(mockAuthorAgeNotANumber)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("O gênero do Autor deve ser 'MASCULINO', 'FEMININO' ou 'OUTROS'"));
    }

    @Test
    @DisplayName("[GET] Deve retornar 200 ao buscar um Autor pelo ID informado")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertAuthors),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB),
    })
    public void shouldReturn200_GetAuthorById() throws Exception {
        final AuthorResponseDTO mockAuthorResponseDTO = AuthorStub.createAuthorResponseDTO();
        mockMvc.perform(get(PATH + "/{authorId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(mockAuthorResponseDTO.getName()))
                .andExpect(jsonPath("$.age").value(mockAuthorResponseDTO.getAge()));
    }

    @Test
    @DisplayName("[GET] Deve retornar 404 ao buscar um Autor para ID inexistente")
    public void shouldReturn404_GetAuthorById() throws Exception {
        mockMvc.perform(get(PATH + "/{authorId}", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Não foi localizado nenhum autor com o id: #1"));
    }

    @Test
    @DisplayName("[PUT] Deve retornar um 200 ao atualizar um Autor para o ID informado")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertAuthors),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB),
    })
    public void shouldReturn200_UpdateAuthorById() throws Exception {
        final UpdateAuthorDTO mockUpdateAuthorDTO = AuthorStub.updateAuthorDTO();

        mockMvc.perform(put(PATH + "/{authorId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( json(mockUpdateAuthorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(mockUpdateAuthorDTO.getName()))
                .andExpect(jsonPath("$.age").value(mockUpdateAuthorDTO.getAge()));
    }

    @Test
    @DisplayName("[PUT] Deve retornar um 404 ao tentar atualizar um Autor com um ID inexistente")
    public void shouldReturn404_UpdateAuthorById() throws Exception {
        final UpdateAuthorDTO mockUpdateAuthorDTO = AuthorStub.updateAuthorDTO();
        Long id = 1L;

        mockMvc.perform(put(PATH + "/{authorId}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( json(mockUpdateAuthorDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Não foi localizado nenhum autor com o id: #" + id));
    }


}
