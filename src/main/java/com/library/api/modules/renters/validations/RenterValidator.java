package com.library.api.modules.renters.validations;

public interface RenterValidator<RenterValidationDTO> {
    void validate(RenterValidationDTO dto);
}
