package com.library.api.v2.domain.commons.entities;

import com.library.api.v2.domain.authors.enums.Genders;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class Person {
    @Embedded
    private Name name;
    @Enumerated(EnumType.STRING)
    private Genders gender;
    private String cpf;
    private LocalDate birthdate;
}
