package com.library.api.v2.domain.authors.enums;

import com.library.api.helpers.exceptions.BadRequestException;

import java.util.Arrays;

import static com.library.api.helpers.constants.MessageConstants.INVALID_GENDER;

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

    public static Genders fromString(String gender) {
        if (gender == null || !isValid(gender))
            throw new BadRequestException(String.format(INVALID_GENDER, Arrays.toString(Genders.values())));

        return Genders.valueOf(gender.toUpperCase());
    }
}
