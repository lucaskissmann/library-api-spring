package com.library.api.helpers.exceptions;

import org.springframework.http.HttpStatus;

public class IllegalArgumentException extends RuntimeException {
    /**
     * IllegalArgumentException
     *
     * @param message String
     */
    public IllegalArgumentException(String message )
    {
        super( message );
    }

    /**
     * getStatus
     *
     * @return getStatus
     */
    public HttpStatus getStatus()
    {
        return HttpStatus.BAD_REQUEST;
    }

}
