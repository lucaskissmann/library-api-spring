package com.library.api.context;

import java.time.LocalDate;

public class ApplicationContext {
    public static final String GOOGLE_API = "https://www.googleapis.com/books/v1/volumes?q=isbn:";

    public static LocalDate today() {
        return LocalDate.now();
    }
}
