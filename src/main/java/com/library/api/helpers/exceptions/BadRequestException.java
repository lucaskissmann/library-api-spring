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
     * getStatus
     *
     * @return getStatus
     */
    public HttpStatus getStatus()
    {
        return HttpStatus.BAD_REQUEST;
    }
}
