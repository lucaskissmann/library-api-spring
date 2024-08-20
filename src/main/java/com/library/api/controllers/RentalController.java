package com.library.api.controllers;

import com.library.api.modules.rentals.dtos.RentalRequestDTO;
import com.library.api.modules.rentals.dtos.RentalResponseDTO;
import com.library.api.services.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.Control;
import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController
    extends
        Controller {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping()
    public ResponseEntity<RentalResponseDTO> create(@RequestBody RentalRequestDTO dto) {
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
    public ResponseEntity<RentalResponseDTO> returnRental(@PathVariable Long id) {
        return ok(rentalService.returnBooks(id));
    }


}
