package com.library.api.context;

import java.time.LocalDate;

public class ApplicationContext {
    private static final ApplicationContext INSTANCE = new ApplicationContext();

    public static final String VERSION = "v1";

    private final String googleApiUrl;

    private ApplicationContext() {
        this.googleApiUrl = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
    }

    public static ApplicationContext getInstance() {
        return INSTANCE;
    }

    public LocalDate today() {
        return LocalDate.now();
    }

    public String getGoogleApiUrl() {
        return googleApiUrl;
    }
}
