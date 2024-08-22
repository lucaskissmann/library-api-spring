package com.library.api.books.validations;

import com.library.api.modules.books.validations.ISBNValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ISBNValidatorTest {

    private ISBNValidator isbnValidator;
    private HttpClient httpClientMock;
    private String validIsbn;
    private String invalidIsbn;

    @BeforeEach
    public void setUp() {
        httpClientMock = mock(HttpClient.class);
        isbnValidator = new ISBNValidator(httpClientMock);
        validIsbn = "978-3-16-148410-0";
        invalidIsbn = "987987987987987";
    }

    @Test
    public void shouldNotThrowExceptionToValidISBN() throws IOException, InterruptedException {
        mockHttpResponse(200, "{\"totalItems\": 1}");

        boolean isValid = isbnValidator.isValid(validIsbn, null);

        assertTrue(isValid);
    }

    @Test
    public void shouldThrowExceptionToInvalidISBN() throws IOException, InterruptedException {
        mockHttpResponse(200, "{\"totalItems\": 0}");

        boolean isValid = isbnValidator.isValid(invalidIsbn, null);

        assertFalse(isValid);
    }

    private void mockHttpResponse(int statusCode, String body) throws IOException, InterruptedException {
        HttpResponse<String> httpResponseMock = mock(HttpResponse.class);
        when(httpResponseMock.statusCode()).thenReturn(statusCode);
        when(httpResponseMock.body()).thenReturn(body);
        when(httpClientMock.send(any(), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponseMock);
    }
}
