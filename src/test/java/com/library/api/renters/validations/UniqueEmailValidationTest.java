package com.library.api.renters.validations;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.modules.renters.Renter;
import com.library.api.modules.renters.validations.RenterValidationDTO;
import com.library.api.modules.renters.validations.UniqueEmailValidation;
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

public class UniqueEmailValidationTest {

    private Renter createRenterStub;
    private Renter createRenterStubNotUniqueEmail;

    private RenterValidationDTO createRenterValidationStub;
    private RenterValidationDTO createRenterValidationNotUniqueEmail;

    @Mock
    private RenterRepository renterRepository;

    @InjectMocks
    private UniqueEmailValidation uniqueEmailValidation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        createRenterStub = RenterStub.createRenterStub();
        createRenterStubNotUniqueEmail = RenterStub.createRenterStubNotUniqueEmail();

        createRenterValidationStub = RenterValidationStub.createRenterValidationDTO();
        createRenterValidationNotUniqueEmail = RenterValidationStub.createRenterValidationDTONotUniqueEmail();
    }

    @Test
    @DisplayName("[VALIDATION] Não deve lançar nenhuma exception para um email inexistente no banco.")
    public void shouldNotThrowExceptionWhenEmailIsUnique() {
        when(renterRepository.findByEmail(createRenterStub.getEmail())).thenReturn(Optional.empty());

        uniqueEmailValidation.validate(createRenterValidationStub);
    }

    @Test
    @DisplayName("[VALIDATION] Deve lançar exception para um email já registrado.")
    public void shouldThrowExceptionWhenThereIsAnotherRegisterWithSameEmail() {
        when(renterRepository.findByEmail(createRenterStubNotUniqueEmail.getEmail())).thenReturn(Optional.of(createRenterStub));

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> uniqueEmailValidation.validate(createRenterValidationNotUniqueEmail));

        assertEquals(exception.getMessage(), "Já existe um Locatário cadastrado para o email '" + createRenterValidationNotUniqueEmail.getEmail() + "'");
    }

    @Test
    @DisplayName("[VALIDATION] Não deve lançar exception caso o email duplicado seja do próprio solicitante.")
    public void shouldNotThrowExceptionWhenDuplicateEmailBelongsToTheRenterItself() {
        when(renterRepository.findByEmail(createRenterStub.getEmail())).thenReturn(Optional.of(createRenterStubNotUniqueEmail));

        uniqueEmailValidation.validate(createRenterValidationNotUniqueEmail);
    }
}
