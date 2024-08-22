package com.library.api.services;

import com.library.api.modules.renters.Renter;
import com.library.api.modules.renters.dtos.RenterRequestDTO;
import com.library.api.modules.renters.dtos.RenterResponseDTO;
import com.library.api.modules.renters.dtos.UpdateRenterDTO;

import java.util.List;

public interface RenterService {
    RenterResponseDTO create(RenterRequestDTO dto);
    RenterResponseDTO update(UpdateRenterDTO updateDto, Long renterId);
    List<RenterResponseDTO> getRenters();
    RenterResponseDTO getRenter(Long id);
    Renter getRenterById(Long id);
    void deleteRenter(Long id);
}
