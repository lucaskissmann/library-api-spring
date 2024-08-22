package com.library.api.services;

import com.library.api.helpers.exceptions.NotFoundException;
import com.library.api.modules.renters.Renter;
import com.library.api.modules.renters.dtos.RenterRequestDTO;
import com.library.api.modules.renters.dtos.RenterResponseDTO;
import com.library.api.modules.renters.dtos.UpdateRenterDTO;
import com.library.api.modules.renters.mappers.RenterMapper;
import com.library.api.modules.renters.validations.RemoveRenterValidator;
import com.library.api.repositories.RenterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RenterServiceImpl implements RenterService {

    private final RenterRepository renterRepository;
    private final RemoveRenterValidator removeRenterValidator;
    private final RenterMapper renterMapper = RenterMapper.INSTANCE;

    public RenterServiceImpl(RenterRepository renterRepository, RemoveRenterValidator removeRenterValidator) {
        this.renterRepository = renterRepository;
        this.removeRenterValidator = removeRenterValidator;
    }

    @Override
    public RenterResponseDTO create(RenterRequestDTO dto) {
        Renter renter = renterMapper.toEntity(dto);
        renterRepository.save(renter);

        return renterMapper.toResponseDto(renter);
    }

    @Override
    public RenterResponseDTO update(UpdateRenterDTO updateDto, Long renterId) {
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
