package com.library.api.v1.modules.renters.validations;

public interface RenterValidator<RenterValidationDTO> {
    void validate(RenterValidationDTO dto);
}
