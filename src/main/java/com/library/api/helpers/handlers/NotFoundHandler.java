package com.library.api.helpers.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.library.api.helpers.Error;
import com.library.api.helpers.exceptions.NotFoundException;

@ControllerAdvice
public class NotFoundHandler
    implements
        Handler<NotFoundException>
{
    /**
     * handle
     *
     * @param e NotFoundException
     * @return ResponseEntity<Error>
     */
    @Override
    @ExceptionHandler( NotFoundException.class )
    public ResponseEntity<Error> handle(NotFoundException e )
    {
        return response( e.getMessage(), e.getStatus() );
    }
}