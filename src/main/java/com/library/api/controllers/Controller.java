package com.library.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Controller {

    /**
     * created
     *
     * @param body T
     * @return ResponseEntity<T>
     */
    protected <T> ResponseEntity<T> created( T body )
    {
        return new ResponseEntity<>( body, HttpStatus.CREATED );
    }

    /**
     * ok
     *
     * @param body T
     * @return ResponseEntity<T>
     */
    protected <T> ResponseEntity<T> ok( T body )
    {
        return new ResponseEntity<>( body, HttpStatus.OK );
    }


    /**
     * noContent
     *
     * @return ResponseEntity<T>
     */
    protected <T> ResponseEntity<T> noContent()
    {
        return new ResponseEntity<>( HttpStatus.NO_CONTENT );
    }
}