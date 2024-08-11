package com.library.api.helpers.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.library.api.helpers.Error;

import java.util.List;

@ControllerAdvice
public interface Handler<T extends Exception>
{
    /**
     * handle
     *
     * @param e T
     * @return ResponseEntity<Error>
     */
    ResponseEntity<Error> handle(T e );

    /**
     * response
     *
     * @param message String
     * @param status HttpStatus
     * @return ResponseEntity<Error>
     */
    default ResponseEntity<Error> response(String message, HttpStatus status )
    {
        return new ResponseEntity<Error>( new Error( status, message ), status );
    }

    /**
     * response with errors
     *
     * @param message String
     * @param status  HttpStatus
     * @param errors  List of error messages
     * @return ResponseEntity<Error>
     */
    default ResponseEntity<Error> responseWithErrors(String message, HttpStatus status, List<String> errors) {
        return new ResponseEntity<>(new Error(status, message, errors), status);
    }
}
