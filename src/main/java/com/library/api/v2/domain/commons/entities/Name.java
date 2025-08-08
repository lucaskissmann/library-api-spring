package com.library.api.v2.domain.commons.entities;

import com.library.api.helpers.exceptions.IllegalArgumentException;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Name {
    private String firstName;
    private String lastName;

    public Name (String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome completo não pode ser vazio");
        }

        var parts = fullName.trim().split("\\s+");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Deve conter nome e sobrenome");
        }

        this.firstName = parts[0];
        this.lastName = parts[parts.length - 1];
    }

    public String getInitials() {
        StringBuilder sb = new StringBuilder();

        sb.append(firstName.charAt(0));
        sb.append(lastName.charAt(0));

        return sb.toString().toUpperCase();
    }
}
