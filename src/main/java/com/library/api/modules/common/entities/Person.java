package com.library.api.modules.common.entities;

import com.library.api.modules.authors.enums.Genders;
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
    private String name;

    @Enumerated(EnumType.STRING)
    private Genders gender;

    private String cpf;
}
