package com.library.api.modules.renters;

import com.library.api.modules.common.entities.Person;
import com.library.api.modules.rentals.Rental;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "renters")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Renter extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;

    private String email;

    private LocalDate birthDate;

    @OneToMany(mappedBy = "renter", cascade = CascadeType.ALL)
    private List<Rental> rentals;
}
