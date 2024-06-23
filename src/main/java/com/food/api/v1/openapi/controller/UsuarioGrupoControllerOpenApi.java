package com.food.api.v1.openapi.controller;

import com.food.api.exceptionhandler.Problem;
import com.food.api.v1.model.response.GrupoResponse;
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

import static com.food.config.OpenApiConfig.TAG_USUARIOS;

@SecurityRequirement(name = OpenApiConfig.SECURITY_AUTH)
@Tag(name = TAG_USUARIOS)
public interface UsuarioGrupoControllerOpenApi {

    @Operation(summary = "Lista os grupos associados a um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Usuário não encontrado")
    })
    CollectionModel<GrupoResponse> listar(@Parameter(description = "ID do usuário", example = "1", required = true)
                               Long usuarioId);

    @Operation(summary = "Desassociação de grupo com usuário")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Desassociação realizada com sucesso"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Usuário ou grupo não encontrado")
    })
    ResponseEntity<Void> desassociar(@Parameter(description = "ID do usuário", example = "1", required = true)
                     Long usuarioId,
                     @Parameter(description = "ID do grupo", example = "1", required = true)
                     Long grupoId);

    @Operation(summary = "Associação de grupo com usuário")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Associação realizada com sucesso"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Usuário ou grupo não encontrado")
    })
    ResponseEntity<Void> associar(@Parameter(description = "ID do usuário", example = "1", required = true)
                  Long usuarioId,
                                  @Parameter(description = "ID do grupo", example = "1", required = true)
                  Long grupoId);
}
