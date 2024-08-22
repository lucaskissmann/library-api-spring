package com.library.api.services;

import com.library.api.modules.rentals.dtos.RentalRequestDTO;
import com.library.api.modules.rentals.dtos.RentalResponseDTO;

import java.util.List;

public interface RentalService {
    RentalResponseDTO create(RentalRequestDTO dto);
    List<RentalResponseDTO> getRentals();
    RentalResponseDTO getRental(Long id);
    RentalResponseDTO returnBooks(Long id);
}
