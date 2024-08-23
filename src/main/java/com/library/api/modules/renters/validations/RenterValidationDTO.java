package com.library.api.modules.renters.validations;

import com.library.api.modules.renters.dtos.RenterRequestDTO;
import com.library.api.modules.renters.dtos.UpdateRenterDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RenterValidationDTO {
    private Long id;
    private String email;
    private String cpf;

    public RenterValidationDTO(RenterRequestDTO dto) {
        this.id = null;
        this.email = dto.getEmail();
        this.cpf = dto.getCpf();
    }

    public RenterValidationDTO(UpdateRenterDTO dto, Long id) {
        this.id = id;
        this.email = dto.getEmail();
        this.cpf = dto.getCpf();
    }
}
