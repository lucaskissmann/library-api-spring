package com.library.api.v2.application.authors.controllers;


import com.library.api.configs.SwaggerConfig;
import com.library.api.v2.application.authors.controllers.dtos.AuthorRequestDTO;
import com.library.api.v2.application.authors.controllers.dtos.AuthorResponseDTO;
import com.library.api.v2.application.authors.controllers.dtos.UpdateAuthorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface AuthorSwagger {

    String TAG_NAME = "Autores";

    @Operation(
            operationId = "createAuthor",
            summary = "Cria um novo autor",
            tags = { TAG_NAME },
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = SwaggerConfig.SUCCESS_MESSAGE,
                            content = @Content(
                                    schema = @Schema(implementation = AuthorResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = SwaggerConfig.BAD_REQUEST_MESSAGE,
                            content = @Content(
                                    schema = @Schema(implementation = Error.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    ResponseEntity<AuthorResponseDTO> create(@Valid @RequestBody AuthorRequestDTO dto);

    @Operation(
            operationId = "updateAuthor",
            summary = "Atualiza um autor existente",
            tags = { TAG_NAME },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = SwaggerConfig.SUCCESS_MESSAGE,
                            content = @Content(
                                    schema = @Schema(implementation = AuthorResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = SwaggerConfig.NOT_FOUND_MESSAGE,
                            content = @Content(
                                    schema = @Schema(implementation = Error.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    ResponseEntity<AuthorResponseDTO> update(@Valid @RequestBody UpdateAuthorDTO updateDto, @PathVariable UUID id);

    @Operation(
            operationId = "getAuthors",
            summary = "Lista todos os autores, filtrando opcionalmente por nome",
            tags = { TAG_NAME },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = SwaggerConfig.SUCCESS_MESSAGE,
                            content = @Content(
                                    schema = @Schema(implementation = AuthorResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    ResponseEntity<List<AuthorResponseDTO>> getAuthors(@RequestParam(required = false) String name);

    @Operation(
            operationId = "getAuthorById",
            summary = "Busca um autor pelo ID",
            tags = { TAG_NAME },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = SwaggerConfig.SUCCESS_MESSAGE,
                            content = @Content(
                                    schema = @Schema(implementation = AuthorResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = SwaggerConfig.NOT_FOUND_MESSAGE,
                            content = @Content(
                                    schema = @Schema(implementation = Error.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    ResponseEntity<AuthorResponseDTO> getAuthor(@PathVariable UUID id);

    @Operation(
            operationId = "deleteAuthor",
            summary = "Remove um autor pelo ID",
            tags = { TAG_NAME },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = SwaggerConfig.SUCCESS_MESSAGE
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = SwaggerConfig.NOT_FOUND_MESSAGE,
                            content = @Content(
                                    schema = @Schema(implementation = Error.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
