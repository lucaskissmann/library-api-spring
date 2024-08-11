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
     * NotFoundException
     *
     * @param cause Throwable
     */
    public NotFoundException( Throwable cause )
    {
        super( cause );
    }

    /**
     * NotFoundException
     *
     * @param message String
     * @param cause Throwable
     */
    public NotFoundException( String message, Throwable cause )
    {
        super( message, cause );
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
