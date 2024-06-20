package com.food.api.v1.openapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;

import static com.food.config.OpenApiConfig.TAG_RESTRAURANTE;

@Tag(name = TAG_RESTRAURANTE)
public interface RestauranteUsuarioResponsavelControllerOpenApi {

    @Operation(summary = "Lista os usuários responsáveis associados a restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    RepresentationModel listar(@Parameter(name = "ID do restaurante", example = "1", required = true)
                                         Long restauranteId);

    @Operation(summary = "Desassociação de restaurante com usuário responsável")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Desassociação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado")
    })
    ResponseEntity<Void> desassociar(@Parameter(name = "ID do restaurante", example = "1", required = true)
                             Long restauranteId,
                     @Parameter(name = "ID do usuário", example = "1", required = true)
                             Long usuarioId);

    @Operation(summary = "Associação de restaurante com usuário responsável")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Associação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado")
    })
    ResponseEntity<Void> associar(@Parameter(name = "ID do restaurante", example = "1", required = true)
                          Long restauranteId,
                                  @Parameter(name = "ID do usuário", example = "1", required = true)
                          Long usuarioId);
}
