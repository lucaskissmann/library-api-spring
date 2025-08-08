package com.library.api.v1.modules.renters.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RenterResponseDTO {
    private Long id;
    private String name;
    private String gender;
    private String phone;
    private String email;
    private String birthDate;
    private String cpf;
}
