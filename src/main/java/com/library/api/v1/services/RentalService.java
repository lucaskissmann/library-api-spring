package com.library.api.v1.services;

import com.library.api.v1.modules.rentals.dtos.RentalRequestDTO;
import com.library.api.v1.modules.rentals.dtos.RentalResponseDTO;
import com.library.api.v1.modules.rentals.dtos.ReturnRentalDTO;

import java.util.List;

public interface RentalService {
    RentalResponseDTO create(RentalRequestDTO dto);
    List<RentalResponseDTO> getRentals();
    RentalResponseDTO getRental(Long id);
    RentalResponseDTO returnBooks(Long id, ReturnRentalDTO dto);
}
