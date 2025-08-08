package com.library.api.v1.modules.renters;

import com.library.api.v1.modules.common.entities.Person;
import com.library.api.v1.modules.rentals.Rental;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
