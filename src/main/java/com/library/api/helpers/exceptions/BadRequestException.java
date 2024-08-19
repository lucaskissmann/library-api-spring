package com.library.api.helpers.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException
    extends
        RuntimeException
{
    /**
     * BadRequestException
     *
     * @param message String
     */
    public BadRequestException(String message )
    {
        super( message );
    }

    /**
     * BadRequestException
     *
     * @param cause Throwable
     */
    public BadRequestException(Throwable cause )
    {
        super( cause );
    }

    /**
     * BadRequestException
     *
     * @param message String
     * @param cause Throwable
     */
    public BadRequestException(String message, Throwable cause )
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
        return HttpStatus.BAD_REQUEST;
    }
}
