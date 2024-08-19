package com.library.api.context;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ApplicationContext {
    public static final String GOOGLE_API = "https://www.googleapis.com/books/v1/volumes?q=isbn:";

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static LocalDate today() {
        return LocalDate.now();
    }
}
