package com.library.api.renters.validations;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.modules.renters.Renter;
import com.library.api.modules.renters.validations.RemoveRenterValidator;
import com.library.api.rentals.stubs.RentalStub;
import com.library.api.renters.stubs.RenterStub;
import com.library.api.repositories.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@ActiveProfiles("test")
public class RemoveRenterValidatorTest {

    private Renter renterStub;

    @Mock
    private RentalRepository rentalRepository;

    @InjectMocks
    private RemoveRenterValidator removeRenterValidator;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        renterStub = RenterStub.createRenterStub();
    }

    @Test
    @DisplayName("[VALIDATION] Deve lançar uma exceção ao tentar remover um locatário que possui aluguel ativo.")
    public void shouldThrowBadRequestExceptionWhenRenterHasActiveRentals() {
        when(rentalRepository.findByRenterAndIsReturned(renterStub, false)).thenReturn(List.of(RentalStub.createRentalStub()));

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> removeRenterValidator.validate(renterStub));

        assertEquals("Não foi possível remover o locatário '" + renterStub.getName() + "' pois no momento ele está vinculado a um aluguel ativo",
                exception.getMessage());
    }

    @Test
    @DisplayName("[VALIDATION] Não deve lançar uma exceção ao tentar remover um locatário que não possui nenhum aluguel ativo")
    public void shouldThrowBadRequestExceptionWhenRenterHasNotActiveRentals() {
        when(rentalRepository.findByRenterAndIsReturned(renterStub, false)).thenReturn(Collections.emptyList());

        removeRenterValidator.validate(renterStub);

        verify(rentalRepository, times(1)).findByRenterAndIsReturned(renterStub, false);
    }
}
