package com.food.api.v1.openapi.controller;

import com.food.api.exceptionhandler.Problem;
import com.food.api.v1.model.response.PermissaoResponse;
import com.food.config.OpenApiConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import static com.food.config.OpenApiConfig.TAG_GRUPO;

@SecurityRequirement(name = OpenApiConfig.SECURITY_AUTH)
@Tag(name = TAG_GRUPO)
public interface GrupoPermissaoControllerOpenApi {

    @Operation(summary = "Lista as permissões associadas a um grupo")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "ID do grupo inválido"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Grupo não encontrado")
    })
    CollectionModel<PermissaoResponse> listar(@Parameter(description = "ID do grupo", example = "1", required = true)
                                   Long grupoId);

    @Operation(summary = "Desassociação de permissão com grupo")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Desassociação realizada com sucesso"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Grupo ou permissão não encontrada")
    })
    ResponseEntity<Void> desassociar(@Parameter(description = "ID do grupo", example = "1", required = true)
                     Long grupoId,
                                     @Parameter(description = "ID da permissão", example = "1", required = true)
                     Long permissaoId);

    @Operation(summary = "Associação de permissão com grupo")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Associação realizada com sucesso"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Grupo ou permissão não encontrada")
    })
    ResponseEntity<Void> associar(
            @Parameter(description = "ID do grupo", example = "1", required = true)
                    Long grupoId,

            @Parameter(description = "ID da permissão", example = "1", required = true)
                    Long permissaoId);
}
