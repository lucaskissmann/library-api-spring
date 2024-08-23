package com.library.api.modules.renters.validations;

import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.modules.renters.Renter;
import com.library.api.repositories.RenterRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component(value = "uniqueCpfRenterValidation")
public class UniqueCpfValidation implements RenterValidator<RenterValidationDTO> {

    private final RenterRepository renterRepository;

    public UniqueCpfValidation(RenterRepository renterRepository) {
        this.renterRepository = renterRepository;
    }

    @Override
    public void validate(RenterValidationDTO renterDTO) {
        if(renterDTO.getCpf() == null)
            return;

        Optional<Renter> findAuthor = renterRepository.findByCpf(renterDTO.getCpf().replaceAll("\\D", ""));
        if(findAuthor.isPresent() && !Objects.equals(findAuthor.get().getId(), renterDTO.getId())) {
            throw new BadRequestException("Já existe um Locatário cadastrado para o cpf '" + renterDTO.getCpf() + "'");
        }
    }

}
