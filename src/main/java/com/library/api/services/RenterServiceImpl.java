package com.library.api.services;

import com.library.api.helpers.exceptions.NotFoundException;
import com.library.api.modules.authors.validations.AuthorValidationDTO;
import com.library.api.modules.renters.Renter;
import com.library.api.modules.renters.dtos.RenterRequestDTO;
import com.library.api.modules.renters.dtos.RenterResponseDTO;
import com.library.api.modules.renters.dtos.UpdateRenterDTO;
import com.library.api.modules.renters.mappers.RenterMapper;
import com.library.api.modules.renters.validations.RemoveRenterValidator;
import com.library.api.modules.renters.validations.RenterValidationDTO;
import com.library.api.modules.renters.validations.RenterValidator;
import com.library.api.repositories.RenterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RenterServiceImpl implements RenterService {

    private final RenterRepository renterRepository;
    private final List<RenterValidator<RenterValidationDTO>> validators;
    private final RemoveRenterValidator removeRenterValidator;
    private final RenterMapper renterMapper = RenterMapper.INSTANCE;

    public RenterServiceImpl(RenterRepository renterRepository, RemoveRenterValidator removeRenterValidator, List<RenterValidator<RenterValidationDTO>> validators) {
        this.renterRepository = renterRepository;
        this.removeRenterValidator = removeRenterValidator;
        this.validators = validators;
    }

    @Override
    public RenterResponseDTO create(RenterRequestDTO dto) {
        validators.forEach(validator -> validator.validate(new RenterValidationDTO(dto)));

        Renter renter = renterMapper.toEntity(dto);
        renterRepository.save(renter);

        return renterMapper.toResponseDto(renter);
    }

    @Override
    public RenterResponseDTO update(UpdateRenterDTO updateDto, Long renterId) {
        validators.forEach(validator -> validator.validate(new RenterValidationDTO(updateDto, renterId)));

        Renter renter = getRenterById(renterId);

        renterMapper.updateEntityFromDto(renter, updateDto);

        renterRepository.save(renter);

        return renterMapper.toResponseDto(renter);
    }

    @Override
    public Renter getRenterById(Long id) {
        return renterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Não foi localizado nenhum locatário com o id: #" + id));
    }

    @Override
    public List<RenterResponseDTO> getRenters() {
        return renterMapper.toResponseDto(renterRepository.findAll());
    }

    @Override
    public RenterResponseDTO getRenter(Long id) {
        return renterMapper.toResponseDto(getRenterById(id));
    }

    @Override
    public void deleteRenter(Long id) {
        Renter renter = getRenterById(id);

        removeRenterValidator.validate(renter);

        renterRepository.delete(renter);
    }
}
