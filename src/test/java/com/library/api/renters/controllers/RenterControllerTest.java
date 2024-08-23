package com.library.api.renters.controllers;

import com.library.api.helpers.QueryProvider;
import com.library.api.modules.renters.dtos.RenterRequestDTO;
import com.library.api.modules.renters.dtos.RenterResponseDTO;
import com.library.api.modules.renters.dtos.UpdateRenterDTO;
import com.library.api.renters.stubs.RenterStub;
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
public class RenterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String PATH = "/renters";

    private final RenterRequestDTO mockRenterDTO = RenterStub.createRenterRequestDTO();

    @Test
    @DisplayName("[POST] Deve retornar 201 ao criar um Renter")
    public void shouldReturn201_CreateRenter() throws Exception {
        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(mockRenterDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(mockRenterDTO.getName()))
                .andExpect(jsonPath("$.email").value(mockRenterDTO.getEmail()));
    }

    @Test
    @DisplayName("[POST] Deve retornar 400 ao tentar criar Renter com email duplicado")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertRenters),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB),
    })
    public void shouldReturn400_CreateRenterInvalidEmail() throws Exception {
        final RenterRequestDTO renterRequestDTO = RenterStub.createRenterRequestDTO();

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(renterRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Já existe um Locatário cadastrado para o email '" + renterRequestDTO.getEmail() + "'"));
    }

    @Test
    @DisplayName("[GET] Deve retornar 200 ao buscar um Renter pelo ID informado")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertRenters),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB),
    })
    public void shouldReturn200_GetRenterById() throws Exception {
        final RenterResponseDTO mockRenterResponseDTO = RenterStub.createRenterResponseDTO();
        mockMvc.perform(get(PATH + "/{renterId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(mockRenterResponseDTO.getName()))
                .andExpect(jsonPath("$.gender").value(mockRenterResponseDTO.getGender()))
                .andExpect(jsonPath("$.email").value(mockRenterResponseDTO.getEmail()));
    }

    @Test
    @DisplayName("[GET] Deve retornar 404 ao buscar um Renter para ID inexistente")
    public void shouldReturn404_GetRenterById() throws Exception {
        mockMvc.perform(get(PATH + "/{renterId}", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Não foi localizado nenhum locatário com o id: #1" ));
    }

    @Test
    @DisplayName("[PUT] Deve retornar 200 ao atualizar um Renter para o ID informado")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertRenters),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB),
    })
    public void shouldReturn200_UpdateRenterById() throws Exception {
        final UpdateRenterDTO mockUpdateRenterDTO = RenterStub.updateRenterDTO();

        mockMvc.perform(put(PATH + "/{renterId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(mockUpdateRenterDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(mockUpdateRenterDTO.getName()))
                .andExpect(jsonPath("$.email").value(mockUpdateRenterDTO.getEmail()));
    }

    @Test
    @DisplayName("[PUT] Deve retornar 404 ao tentar atualizar um Renter com um ID inexistente")
    public void shouldReturn404_UpdateRenterById() throws Exception {
        final UpdateRenterDTO mockUpdateRenterDTO = RenterStub.updateRenterDTO();
        mockMvc.perform(put(PATH + "/{renterId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(mockUpdateRenterDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Não foi localizado nenhum locatário com o id: #1"));
    }

    @Test
    @DisplayName("[DELETE] Deve retornar 204 ao deletar um Renter com o ID informado")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertRenters),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB),
    })
    public void shouldReturn204_DeleteRenterById() throws Exception {
        mockMvc.perform(delete(PATH + "/{renterId}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("[DELETE] Deve retornar 404 ao tentar deletar um Renter com um ID inexistente")
    public void shouldReturn404_DeleteRenterById() throws Exception {
        mockMvc.perform(delete(PATH + "/{renterId}", 2))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Não foi localizado nenhum locatário com o id: #2" ));
    }
}

