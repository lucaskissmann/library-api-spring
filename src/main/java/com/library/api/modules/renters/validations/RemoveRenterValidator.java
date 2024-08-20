package com.library.api.modules.renters.validations;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.modules.renters.Renter;
import com.library.api.repositories.RentalRepository;
import org.springframework.stereotype.Component;

@Component
public class RemoveRenterValidator {

    private final RentalRepository rentalRepository;

    public RemoveRenterValidator(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void validate(Renter renter) {
        if(!rentalRepository.findByRenterAndIsReturned(renter, false).isEmpty()) {
            throw new BadRequestException("Não foi possível remover o locatário '" + renter.getName() + "' pois no momento ele está vinculado a um aluguel ativo");
        }
    }
}
