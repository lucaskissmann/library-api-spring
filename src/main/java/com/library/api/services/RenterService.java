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
public class RenterService {

    private final RenterRepository renterRepository;
    private final RemoveRenterValidator removeRenterValidator;
    private final RenterMapper renterMapper = RenterMapper.INSTANCE;

    public RenterService(RenterRepository renterRepository, RemoveRenterValidator removeRenterValidator) {
        this.renterRepository = renterRepository;
        this.removeRenterValidator = removeRenterValidator;
    }

    public RenterResponseDTO create(RenterRequestDTO dto) {
        Renter renter = renterMapper.toEntity(dto);
        renterRepository.save(renter);

        return renterMapper.toResponseDto(renter);
    }

    public RenterResponseDTO update(UpdateRenterDTO updateDto, Long renterId) {
        Renter renter = getRenterById(renterId);

        renterMapper.updateEntityFromDto(renter, updateDto);

        renterRepository.save(renter);

        return renterMapper.toResponseDto(renter);
    }

    public Renter getRenterById(Long id) {
        return renterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Não foi localizado nenhum locatário com o id: #" + id));
    }

    public List<RenterResponseDTO> getRenters() {
        return renterMapper.toResponseDto(renterRepository.findAll());
    }

    public RenterResponseDTO getRenter(Long id) {
        return renterMapper.toResponseDto(getRenterById(id));
    }

    public void deleteRenter(Long id) {
        Renter renter = getRenterById(id);

        removeRenterValidator.validate(renter);

//        renterRepository.delete(renter);
    }
}
