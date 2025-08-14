package com.library.api.v2.infra.renters;

import com.library.api.v2.domain.commons.entities.Person;
import com.library.api.v2.infra.rentals.RentalEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity(name = "renters")
public class RenterEntity extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String phone;

    private String email;

    private LocalDate birthDate;

    @OneToMany(mappedBy = "renter", cascade = CascadeType.ALL)
    private List<RentalEntity> rentals;
}
