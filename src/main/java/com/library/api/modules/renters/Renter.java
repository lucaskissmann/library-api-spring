package com.library.api.modules.renters;

import com.library.api.helpers.validations.ValidDate;
import com.library.api.modules.authors.enums.Genders;
import com.library.api.modules.rentals.Rental;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "renters")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Renter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Genders gender;

    private String phone;

    private String email;

    @ValidDate
    private String birthDate;

    private String cpf;

    @OneToMany(mappedBy = "renter", cascade = CascadeType.ALL)
    private List<Rental> rentals;
}
