package com.library.api.v2.domain.authors.enums;

public enum Genders {
    MASCULINO,
    FEMININO,
    OUTROS;

    public static boolean isValid(String value) {
        try {
            valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
