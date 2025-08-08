package com.library.api.v1.controllers;

import com.library.api.context.ApplicationContext;
import com.library.api.v1.modules.rentals.dtos.RentalRequestDTO;
import com.library.api.v1.modules.rentals.dtos.RentalResponseDTO;
import com.library.api.v1.modules.rentals.dtos.ReturnRentalDTO;
import com.library.api.v1.services.RentalServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = ApplicationContext.VERSION + "/rentals")
public class RentalController
    extends
        Controller {

    private final RentalServiceImpl rentalService;

    public RentalController(RentalServiceImpl rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping()
    public ResponseEntity<RentalResponseDTO> create(@Valid @RequestBody RentalRequestDTO dto) {
        return created(rentalService.create(dto));
    }

    @GetMapping()
    public ResponseEntity<List<RentalResponseDTO>> getRentals() {
        return ok(rentalService.getRentals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalResponseDTO> getRental(@PathVariable Long id) {
        return ok(rentalService.getRental(id));
    }

    @PutMapping("/{id}/returns")
    public ResponseEntity<RentalResponseDTO> returnRental(@PathVariable Long id,@Valid @RequestBody ReturnRentalDTO dto) {
        return ok(rentalService.returnBooks(id, dto));
    }
}
