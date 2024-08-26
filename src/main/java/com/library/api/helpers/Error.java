package com.library.api.helpers;


import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class Error
{
    private int code;
    private String status;
    private String message;
    private List<String> errors;

    /**
     * Error
     *
     * @param httpStatus HttpStatus
     * @param message String
     */
    public Error(HttpStatus httpStatus, String message )
    {
        this.code = httpStatus.value();
        this.message = message;
        this.status = httpStatus.getReasonPhrase();
    }

    public Error(HttpStatus httpStatus, String message, List<String> errors) {
        this.code = httpStatus.value();
        this.message = message;
        this.status = httpStatus.getReasonPhrase();
        this.errors = errors != null && !errors.isEmpty() ? errors : null;
    }
}