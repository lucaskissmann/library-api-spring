package com.library.api.modules.books.validations;

import com.library.api.context.ApplicationContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpResponse;

public class ISBNValidator implements ConstraintValidator<ISBN, String> {

    private final HttpClient httpClient;

    public ISBNValidator() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public ISBNValidator(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        if (isbn == null || isbn.isEmpty())
            return true;

        return isISBNValid(isbn);
    }

    private boolean isISBNValid(String isbn) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ApplicationContext.GOOGLE_API + isbn))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200 && response.body().contains("\"totalItems\": 0") == false;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
