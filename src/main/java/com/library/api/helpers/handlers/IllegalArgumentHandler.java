package com.library.api.helpers.handlers;

import com.library.api.helpers.Error;
import com.library.api.helpers.exceptions.BadRequestException;
import com.library.api.helpers.exceptions.IllegalArgumentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class IllegalArgumentHandler
    implements
        Handler<IllegalArgumentException>
{
    /**
     * handle
     *
     * @param e IllegalArgumentHandler
     * @return ResponseEntity<Error>
     */
    @Override
    @ExceptionHandler( IllegalArgumentException.class )
    public ResponseEntity<Error> handle(IllegalArgumentException e )
    {
        return response( e.getMessage(), e.getStatus() );
    }
}