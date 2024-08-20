package com.library.api.modules.renters.dtos;

import com.library.api.helpers.validations.ValidDate;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateRenterDTO {
    private String name;
    private String gender;
    private String phone;
    private String email;
    @ValidDate
    private String birthDate;
    private String cpf;
}
