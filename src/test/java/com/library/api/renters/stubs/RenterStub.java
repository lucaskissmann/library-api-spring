package com.library.api.renters.stubs;

import com.library.api.modules.authors.enums.Genders;
import com.library.api.modules.renters.Renter;
import com.library.api.modules.renters.dtos.RenterRequestDTO;
import com.library.api.modules.renters.dtos.RenterResponseDTO;
import com.library.api.modules.renters.dtos.UpdateRenterDTO;

import java.time.LocalDate;

public interface RenterStub {

    static Renter createRenterStub() {
        return Renter.builder()
                .id(1L)
                .name("Lucas Kissmann")
                .birthDate(LocalDate.parse("2001-05-12"))
                .cpf("56236023042")
                .email("lk@mail.com")
                .gender(Genders.MASCULINO)
                .phone("51998753332")
                .build();
    }

    static Renter createRenterStubNotUniqueCpf() {
        return Renter.builder()
                .id(2L)
                .name("Lucas Kissmann")
                .birthDate(LocalDate.parse("2001-05-12"))
                .cpf("56236023042")
                .email("lk@mail.com.br")
                .gender(Genders.MASCULINO)
                .phone("51998753332")
                .build();
    }

    static Renter createRenterStubNotUniqueEmail() {
        return Renter.builder()
                .id(2L)
                .name("Lucas Kissmann")
                .birthDate(LocalDate.parse("2001-05-12"))
                .cpf("98323215065")
                .email("lk@mail.com")
                .gender(Genders.MASCULINO)
                .phone("51998753332")
                .build();
    }

    static RenterRequestDTO createRenterRequestDTO() {
        return RenterRequestDTO.builder()
                .name("Lucas Kissmann")
                .birthDate("2001-05-12")
                .cpf("56236023042")
                .email("lk@mail.com")
                .gender("MASCULINO")
                .phone("51998753332")
                .build();
    }

    static RenterRequestDTO createInvalidRenterRequestDTO() {
        return RenterRequestDTO.builder()
                .name(null)
                .birthDate(null)
                .cpf(null)
                .email(null)
                .gender(null)
                .phone(null)
                .build();
    }

    static UpdateRenterDTO updateRenterDTO() {
        return UpdateRenterDTO.builder()
                .name("Lucas Updated")
                .birthDate("2001-05-12")
                .cpf("56236023042")
                .email("lk_updated@mail.com")
                .gender("MASCULINO")
                .phone("5499999999")
                .build();
    }

    static RenterResponseDTO createRenterResponseDTO() {
        return RenterResponseDTO.builder()
                .id(1L)
                .name("Lucas Kissmann")
                .birthDate("2001-05-12")
                .cpf("56236023042")
                .email("lk@mail.com")
                .gender("MASCULINO")
                .phone("51998753332")
                .build();
    }
}
