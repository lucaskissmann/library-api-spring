package com.library.api.renters.stubs;

import com.library.api.modules.renters.validations.RenterValidationDTO;

public interface RenterValidationStub {
    static RenterValidationDTO createRenterValidationDTO() {
        RenterValidationDTO renterValidationDTO = new RenterValidationDTO();
        renterValidationDTO.setId(1L);
        renterValidationDTO.setCpf("56236023042");
        renterValidationDTO.setEmail("lk@mail.com");

        return renterValidationDTO;
    }

    static RenterValidationDTO createRenterValidationDTONotUniqueCpf() {
        RenterValidationDTO renterValidationDTO = new RenterValidationDTO();
        renterValidationDTO.setId(2L);
        renterValidationDTO.setCpf("56236023042");
        renterValidationDTO.setEmail("lk@mail.com.br");

        return renterValidationDTO;
    }

    static RenterValidationDTO createRenterValidationDTONotUniqueEmail() {
        RenterValidationDTO renterValidationDTO = new RenterValidationDTO();
        renterValidationDTO.setId(2L);
        renterValidationDTO.setCpf("98323215065");
        renterValidationDTO.setEmail("lk@mail.com");

        return renterValidationDTO;
    }
}
