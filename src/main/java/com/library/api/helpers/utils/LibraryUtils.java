package com.library.api.helpers.utils;

import com.library.api.v2.domain.commons.entities.Name;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LibraryUtils {

    public static Name stringToName(String fullName) {
        if (fullName == null || fullName.isEmpty())
            return null;
        return new Name(fullName);
    }

    public static String nameToString(Name name) {
        if (name == null)
            return null;
        return name.getFullName();
    }
}
