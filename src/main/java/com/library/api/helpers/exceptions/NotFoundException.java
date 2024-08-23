package com.library.api.helpers.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException
    extends
        RuntimeException
{
    /**
     * NotFoundException
     *
     * @param message String
     */
    public NotFoundException( String message )
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
        return HttpStatus.NOT_FOUND;
    }
}
