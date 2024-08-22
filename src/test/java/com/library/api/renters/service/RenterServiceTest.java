package com.library.api.renters.service;

import com.library.api.helpers.exceptions.NotFoundException;
import com.library.api.modules.renters.Renter;
import com.library.api.modules.renters.dtos.RenterRequestDTO;
import com.library.api.modules.renters.dtos.RenterResponseDTO;
import com.library.api.modules.renters.dtos.UpdateRenterDTO;
import com.library.api.modules.renters.validations.RemoveRenterValidator;
import com.library.api.renters.stubs.RenterStub;
import com.library.api.repositories.RenterRepository;
import com.library.api.services.RenterServiceImpl;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("RenterService")
public class RenterServiceTest {

    private Renter renterStub;
    private RenterRequestDTO renterRequestStub;
    private RenterRequestDTO invalidRenterRequestStub;
    private UpdateRenterDTO updateRenterStub;

    private Validator validator;

    @Mock
    private RenterRepository renterRepository;

    @Mock
    private RemoveRenterValidator removeRenterValidator;

    @InjectMocks
    private RenterServiceImpl renterService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = Validation.buildDefaultValidatorFactory().getValidator();

        renterStub = RenterStub.createRenterStub();
        renterRequestStub = RenterStub.createRenterRequestDTO();
        invalidRenterRequestStub = RenterStub.createInvalidRenterRequestDTO();
        updateRenterStub = RenterStub.updateRenterDTO();
    }

    @Test
    @DisplayName("[SERVICE] Deve criar um locatário")
    public void shouldCreateARenter() {
        RenterResponseDTO responseDTO = renterService.create(renterRequestStub);

        assertEquals(renterRequestStub.getName(), responseDTO.getName());
        assertEquals(renterRequestStub.getEmail(), responseDTO.getEmail());
        assertEquals(renterRequestStub.getPhone(), responseDTO.getPhone());
        assertEquals(renterRequestStub.getBirthDate(), responseDTO.getBirthDate());
        assertEquals(renterRequestStub.getGender(), responseDTO.getGender());
        assertEquals(renterRequestStub.getCpf(), responseDTO.getCpf());
        verify(renterRepository, times(1)).save(any(Renter.class));
    }

    @Test
    @DisplayName("[SERVICE] Não deve criar um locatário caso haja dados inválidos")
    public void shouldNotCreateARenter() {
        Set<ConstraintViolation<RenterRequestDTO>> violations = validator.validate(invalidRenterRequestStub);

        assertFalse(violations.isEmpty());
        verify(renterRepository, times(0)).save(any(Renter.class));
    }

    @Test
    @DisplayName("[SERVICE] Não deve encontrar um locatário para o ID informado")
    public void shouldNotFindRenterById() {
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> renterService.getRenter(1L));

        assertEquals("Não foi localizado nenhum locatário com o id: #" + 1L,
                exception.getMessage());
    }

    @Test
    @DisplayName("[SERVICE] Deve encontrar um locatário para o ID informado")
    public void shouldFindRenterById() {
        when(renterRepository.findById(renterStub.getId())).thenReturn(Optional.of(renterStub));

        RenterResponseDTO foundRenter = renterService.getRenter(renterStub.getId());

        assertNotNull(foundRenter);
        assertEquals(renterStub.getId(), foundRenter.getId());
        assertEquals(renterStub.getName(), foundRenter.getName());
        assertEquals(renterStub.getEmail(), foundRenter.getEmail());
        assertEquals(renterStub.getPhone(), foundRenter.getPhone());
        assertEquals(renterStub.getBirthDate().toString(), foundRenter.getBirthDate());
        assertEquals(renterStub.getGender().toString(), foundRenter.getGender());
        assertEquals(renterStub.getCpf(), foundRenter.getCpf());
    }

    @Test
    @DisplayName("[SERVICE] Deve retornar uma lista de locatários")
    public void shouldFindAllRenters() {
        List<Renter> rentersStub = List.of(renterStub);

        when(renterRepository.findAll()).thenReturn(rentersStub);

        List<RenterResponseDTO> foundRenters = renterService.getRenters();

        assertNotNull(foundRenters);
        assertEquals(rentersStub.size(), foundRenters.size());
        assertEquals(rentersStub.get(0).getId(), foundRenters.get(0).getId());
    }

    @Test
    @DisplayName("[SERVICE] Deve atualizar um locatário para o ID informado")
    public void shouldUpdateRenter() {
        Long renterId = 1L;

        when(renterRepository.findById(renterStub.getId())).thenReturn(Optional.of(renterStub));
        when(renterRepository.save(any(Renter.class))).thenReturn(renterStub);

        RenterResponseDTO result = renterService.update(updateRenterStub, renterId);

        assertNotNull(result);
        assertEquals(result.getId(), renterStub.getId());
        assertEquals(result.getName(), updateRenterStub.getName());
        assertEquals(result.getEmail(), updateRenterStub.getEmail());
        assertEquals(result.getPhone(), updateRenterStub.getPhone());
        assertEquals(result.getBirthDate(), updateRenterStub.getBirthDate());
        assertEquals(result.getGender(), updateRenterStub.getGender());
        assertEquals(result.getCpf(), updateRenterStub.getCpf());

        verify(renterRepository).findById(renterId);
        verify(renterRepository).save(renterStub);
    }

    @Test
    @DisplayName("[SERVICE] Deve remover um locatário para o ID informado")
    public void shouldRemoveRenter() {
        Long renterId = 1L;

        when(renterRepository.findById(renterStub.getId())).thenReturn(Optional.of(renterStub));
        doNothing().when(removeRenterValidator).validate(any(Renter.class));

        renterService.deleteRenter(1L);

        verify(renterRepository, times(1)).delete(any(Renter.class));
    }
}
