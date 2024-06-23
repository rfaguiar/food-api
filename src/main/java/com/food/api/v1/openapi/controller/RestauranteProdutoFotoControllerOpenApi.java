package com.food.api.v1.openapi.controller;

import com.food.api.exceptionhandler.Problem;
import com.food.api.v1.model.request.FotoProdutoRequest;
import com.food.api.v1.model.response.FotoProdutoResponse;
import com.food.config.OpenApiConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import static com.food.config.OpenApiConfig.TAG_PRODUTOS;

@SecurityRequirement(name = OpenApiConfig.SECURITY_AUTH)
@Tag(name = TAG_PRODUTOS)
public interface RestauranteProdutoFotoControllerOpenApi {


    @Operation(summary = "Atualiza a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Foto do produto atualizada"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Produto de restaurante não encontrado")
    })
    FotoProdutoResponse atualizarFoto(@Parameter(description = "ID do restaurante", example = "1", required = true)
                                      Long restauranteId,
                                      @Parameter(description = "ID do produto", example = "1", required = true)
                                      Long produtoId,
                                      @RequestBody(required = true)
                                      FotoProdutoRequest fotoProdutoRequest);

    @Operation(description = "Busca a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FotoProdutoResponse.class)),
                    @Content(mediaType = "image/jpeg", schema = @Schema(type = "string", format = "binary")),
                    @Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary"))
            }),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "ID do restaurante ou produto inválido"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Foto de produto não encontrada")
    })
    FotoProdutoResponse buscar(@Parameter(description = "ID do restaurante", example = "1", required = true)
                               Long restauranteId,
                               @Parameter(description = "ID do produto", example = "1", required = true)
                               Long produtoId);

    @Operation(summary = "Exclui a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Foto do produto excluída"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "ID do restaurante ou produto inválido"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Foto de produto não encontrada")
    })
    void excluir(@Parameter(description = "ID do restaurante", example = "1", required = true)
                 Long restauranteId,
                 @Parameter(description = "ID do produto", example = "1", required = true)
                 Long produtoId);
}
