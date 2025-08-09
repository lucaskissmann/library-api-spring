package com.library.api.v2.infra.commons;

import com.library.api.v2.domain.authors.enums.Genders;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDate;

@MappedSuperclass
public abstract class PersonEntity {
    private String name;
    @Enumerated(EnumType.STRING)
    private Genders gender;
    private String cpf;
    private LocalDate birthdate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
