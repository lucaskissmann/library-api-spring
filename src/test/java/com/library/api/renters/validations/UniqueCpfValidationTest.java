package com.library.api.renters.validations;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.modules.renters.Renter;
import com.library.api.modules.renters.validations.RenterValidationDTO;
import com.library.api.modules.renters.validations.UniqueCpfValidation;
import com.library.api.renters.stubs.RenterStub;
import com.library.api.renters.stubs.RenterValidationStub;
import com.library.api.repositories.RenterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UniqueCpfValidationTest {

    private Renter createRenterStub;
    private Renter createRenterStubNotUniqueCpf;

    private RenterValidationDTO createRenterValidationStub;
    private RenterValidationDTO createRenterValidationNotUniqueCpf;

    @Mock
    private RenterRepository renterRepository;

    @InjectMocks
    private UniqueCpfValidation uniqueCpfValidation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        createRenterStub = RenterStub.createRenterStub();
        createRenterStubNotUniqueCpf = RenterStub.createRenterStubNotUniqueCpf();

        createRenterValidationStub = RenterValidationStub.createRenterValidationDTO();
        createRenterValidationNotUniqueCpf = RenterValidationStub.createRenterValidationDTONotUniqueCpf();
    }

    @Test
    @DisplayName("[VALIDATION] Não deve lançar nenhuma exception para um CPF inexistente no banco.")
    public void shouldNotThrowExceptionWhenCpfIsUnique() {
        when(renterRepository.findByCpf(createRenterStub.getCpf())).thenReturn(Optional.empty());

        uniqueCpfValidation.validate(createRenterValidationStub);
    }

    @Test
    @DisplayName("[VALIDATION] Deve lançar exception para um nome já registrado.")
    public void shouldThrowExceptionWhenThereIsAnotherRegisterWithSameCpf() {
        when(renterRepository.findByCpf(createRenterStubNotUniqueCpf.getCpf())).thenReturn(Optional.of(createRenterStub));

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> uniqueCpfValidation.validate(createRenterValidationNotUniqueCpf));

        assertEquals(exception.getMessage(), "Já existe um Locatário cadastrado para o cpf '" + createRenterValidationNotUniqueCpf.getCpf() + "'");
    }

    @Test
    @DisplayName("[VALIDATION] Não deve lançar exception caso o cpf duplicado seja do próprio solicitante.")
    public void shouldNotThrowExceptionWhenDuplicateCpfBelongsToTheRenterItself() {
        when(renterRepository.findByCpf(createRenterStub.getCpf())).thenReturn(Optional.of(createRenterStubNotUniqueCpf));

        uniqueCpfValidation.validate(createRenterValidationNotUniqueCpf);
    }
}
