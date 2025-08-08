package com.library.api.v1.services;

import com.library.api.v1.modules.renters.Renter;
import com.library.api.v1.modules.renters.dtos.RenterRequestDTO;
import com.library.api.v1.modules.renters.dtos.RenterResponseDTO;
import com.library.api.v1.modules.renters.dtos.UpdateRenterDTO;

import java.util.List;

public interface RenterService {
    RenterResponseDTO create(RenterRequestDTO dto);
    RenterResponseDTO update(UpdateRenterDTO updateDto, Long renterId);
    List<RenterResponseDTO> getRenters();
    RenterResponseDTO getRenter(Long id);
    Renter getRenterById(Long id);
    void deleteRenter(Long id);
}
