package com.library.api.v2.domain.commons.entities;

import com.library.api.v1.modules.authors.enums.Genders;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class Person {
    @Embedded
    private Name name;
    @Enumerated(EnumType.STRING)
    private Genders gender;
    private String cpf;
}
