package com.library.api.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    public static final String SUCCESS_MESSAGE = "Operação bem sucedida";
    public static final String NOT_FOUND_MESSAGE = "Objeto não encontrado com informações enviadas";
    public static final String BAD_REQUEST_MESSAGE = "Dados enviados inválidos, confira documentação";
    public static final String UNPROCESSABLE_ENTITY_MESSAGE = "Recurso já existente para atributos enviados";

    @Bean
    OpenAPI apiInfo()
    {
        return new OpenAPI()
                .info( new Info()
                        .title( "Backoffice Biblioteca" )
                        .description("Projeto backend para gerenciamento de biblioteca.")
                        .contact( new Contact()
                                .name( "Lucas Kissmann" )
                                .url( "http://github.com/lucaskissmann" ))
                        .version("1.0")
                        .license( new License() )
                        .termsOfService("") );
    }
}