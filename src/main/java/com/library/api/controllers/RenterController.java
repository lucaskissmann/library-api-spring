package com.library.api.controllers;

import com.library.api.modules.renters.dtos.RenterRequestDTO;
import com.library.api.modules.renters.dtos.RenterResponseDTO;
import com.library.api.modules.renters.dtos.UpdateRenterDTO;
import com.library.api.services.RenterServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/renters")
public class RenterController
    extends
        Controller {

    private final RenterServiceImpl renterService;

    public RenterController(RenterServiceImpl renterService) {
        this.renterService = renterService;
    }

    @PostMapping()
    public ResponseEntity<RenterResponseDTO> create(@Valid @RequestBody RenterRequestDTO dto) {
        return created(renterService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RenterResponseDTO> update(@Valid @RequestBody UpdateRenterDTO dto, @PathVariable Long id) {
        return ok(renterService.update(dto, id));
    }

    @GetMapping()
    public ResponseEntity<List<RenterResponseDTO>> getRenters() {
        return ok(renterService.getRenters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RenterResponseDTO> getRenterById(@PathVariable Long id) {
        return ok(renterService.getRenter(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        renterService.deleteRenter(id);
        return noContent();
    }
}
