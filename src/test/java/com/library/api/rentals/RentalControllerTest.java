package com.library.api.rentals;

import com.library.api.helpers.QueryProvider;
import com.library.api.modules.rentals.dtos.RentalRequestDTO;
import com.library.api.modules.rentals.dtos.RentalResponseDTO;
import com.library.api.rentals.stubs.RentalStub;
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
public class RentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String PATH = "/rentals";

    private final RentalRequestDTO mockRentalDTO = RentalStub.createRentalRequestStub();

    @Test
    @DisplayName("[POST] Deve retornar 201 ao criar um Aluguel")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertAuthors),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertBooks),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertBookMappings),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertRenters),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB),
    })
    public void shouldReturn201_CreateRental() throws Exception {
        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(mockRentalDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.renter.id").value(mockRentalDTO.getRenterId()))
                .andExpect(jsonPath("$.books").isArray())
                .andExpect(jsonPath("$.books.[0].id").value(mockRentalDTO.getBookIds().get(0)))
                .andExpect(jsonPath("$.books.length()").value(mockRentalDTO.getBookIds().size()));
    }

    @Test
    @DisplayName("[GET] Deve retornar 200 ao buscar todos os Aluguéis")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertAuthors),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertBooks),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertBookMappings),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertRenters),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertRentals),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertRentalMappings),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB),
    })
    public void shouldReturn200_GetRentals() throws Exception {
        mockMvc.perform(get(PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("[GET] Deve retornar 200 ao buscar um Aluguel pelo ID informado")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertBooks),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertRenters),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertRentals),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertRentalMappings),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB),
    })
    public void shouldReturn200_GetRentalById() throws Exception {
        final RentalResponseDTO mockRentalResponseDTO = RentalStub.createRentalResponseStub();
        mockMvc.perform(get(PATH + "/{rentalId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.renter.id").value(mockRentalResponseDTO.getRenter().getId()))
                .andExpect(jsonPath("$.books").isArray())
                .andExpect(jsonPath("$.books.[0].id").value(mockRentalResponseDTO.getBooks().get(0).getId()))
                .andExpect(jsonPath("$.books.length()").value(mockRentalResponseDTO.getBooks().size()));
    }

    @Test
    @DisplayName("[GET] Deve retornar 404 ao buscar um Aluguel para ID inexistente")
    public void shouldReturn404_GetRentalById() throws Exception {
        mockMvc.perform(get(PATH + "/{rentalId}", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Aluguel não encontrado para o ID: #1"));
    }

    @Test
    @DisplayName("[PUT] Deve retornar 200 ao devolver um Aluguel para o ID informado")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertRenters),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertRentals),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertBooks),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertRentalMappings),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB),
    })
    public void shouldReturn200_ReturnRental() throws Exception {
        mockMvc.perform(put(PATH + "/{rentalId}/returns", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.isReturned").value(true));
    }

    @Test
    @DisplayName("[PUT] Deve retornar 404 ao tentar retornar um Aluguel com um ID inexistente")
    public void shouldReturn404_ReturnRental() throws Exception {
        mockMvc.perform(put(PATH + "/{rentalId}/returns", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Aluguel não encontrado para o ID: #1"));
    }
}
