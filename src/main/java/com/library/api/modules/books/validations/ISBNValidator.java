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

        try {
            return isISBNValid(isbn);
        } catch (Exception e) {
            //caso a API caia, o isbn não será validado
            return true;
        }
    }

    private boolean isISBNValid(String isbn) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ApplicationContext.GOOGLE_API + isbn))
                .GET()
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200 && response.body().contains("\"totalItems\": 0") == false;

    }
}
