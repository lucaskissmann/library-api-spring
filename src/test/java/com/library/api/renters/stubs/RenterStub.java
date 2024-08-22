package com.library.api.renters.stubs;

import com.library.api.modules.authors.enums.Genders;
import com.library.api.modules.renters.Renter;
import com.library.api.modules.renters.dtos.RenterRequestDTO;
import com.library.api.modules.renters.dtos.UpdateRenterDTO;
import com.library.api.rentals.stubs.RentalStub;

import java.time.LocalDate;
import java.util.List;

public interface RenterStub {

    static Renter createRenterStub() {
        return Renter.builder()
                .id(1L)
                .name("Lucas Kissmann")
                .birthDate(LocalDate.parse("2001-05-12"))
                .cpf("037.333.322-42")
                .email("lk@mail.com")
                .gender(Genders.MASCULINO)
                .phone("51 998753332")
                // stackoverflow
//                 .rentals(List.of(RentalStub.createRentalStub()))
                .build();
    }

    static RenterRequestDTO createRenterRequestDTO() {
        return RenterRequestDTO.builder()
                .name("Lucas Kissmann")
                .birthDate("2001-05-12")
                .cpf("037.333.322-42")
                .email("lk@mail.com")
                .gender("MASCULINO")
                .phone("51 998753332")
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
                .cpf("037.333.322-42")
                .email("lk_updated@mail.com")
                .gender("MASCULINO")
                .phone("5499999999")
                .build();
    }
}
