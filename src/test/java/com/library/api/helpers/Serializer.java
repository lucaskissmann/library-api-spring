package com.library.api.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.library.api.helpers.exceptions.BadRequestException;

public class Serializer
{
    /**
     * json
     *
     * @param objectToConvert T
     * @return String <T>
     */
    public static <T> String json( final T objectToConvert )
    {
        try
        {
            ObjectMapper objectMapper = new ObjectMapper();

            objectMapper.registerModule( new JavaTimeModule() );

            return objectMapper.writeValueAsString( objectToConvert );
        }
        catch( JsonProcessingException e )
        {
            throw new BadRequestException( e.getMessage() );
        }
    }
}
