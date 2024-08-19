package com.library.api.helpers.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.library.api.helpers.Error;
import com.library.api.helpers.exceptions.BadRequestException;

@ControllerAdvice
public class BadRequestHandler
    implements
        Handler<BadRequestException>
{
    /**
     * handle
     *
     * @param e BadRequestException
     * @return ResponseEntity<Error>
     */
    @Override
    @ExceptionHandler( BadRequestException.class )
    public ResponseEntity<Error> handle(BadRequestException e )
    {
        return response( e.getMessage(), e.getStatus() );
    }
}