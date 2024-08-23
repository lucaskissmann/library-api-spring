package com.library.api.modules.renters.validations;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.modules.renters.Renter;
import com.library.api.repositories.RenterRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class UniqueEmailValidation implements RenterValidator<RenterValidationDTO> {

    private final RenterRepository renterRepository;

    public UniqueEmailValidation(RenterRepository renterRepository) {
        this.renterRepository = renterRepository;
    }

    @Override
    public void validate(RenterValidationDTO renterDTO) {
        if(renterDTO.getEmail() == null)
            return;

        Optional<Renter> findAuthor = renterRepository.findByEmail(renterDTO.getEmail());
        if(findAuthor.isPresent() && !Objects.equals(findAuthor.get().getId(), renterDTO.getId())) {
            throw new BadRequestException("Já existe um Locatário cadastrado para o email '" + renterDTO.getEmail() + "'");
        }
    }

}
