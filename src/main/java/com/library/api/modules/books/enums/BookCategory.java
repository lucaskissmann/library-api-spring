package com.library.api.modules.books.enums;

public enum BookCategory {
    FICÇÃO,
    ROMANCE,
    TERROR,
    CIÊNCIA,
    INFANTIL;

    public static boolean isValid(String value) {
        try {
            valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
