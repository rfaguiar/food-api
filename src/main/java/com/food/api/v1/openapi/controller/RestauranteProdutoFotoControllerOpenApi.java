package com.food.api.v1.openapi.controller;

import com.food.api.v1.model.response.FotoProdutoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

import static com.food.config.OpenApiConfig.TAG_PRODUTOS;

@Tag(name = TAG_PRODUTOS)
public interface RestauranteProdutoFotoControllerOpenApi {


    @Operation(summary = "Atualiza a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Foto do produto atualizada"),
            @ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado")
    })
    FotoProdutoResponse atualizarFoto(@Parameter(name = "ID do restaurante", example = "1", required = true)
                                      Long restauranteId,
                                      @Parameter(name = "ID do produto", example = "1", required = true)
                                      Long produtoId,
                                      @Parameter(name = "Descrição da foto do produto", required = true)
                                      String descricao,
                                      @Parameter(name = "Arquivo da foto do produto (máximo 500KB, apenas JPG e PNG)", required = true)
                                      MultipartFile arquivo);

    @Operation(description = "Busca a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido"),
            @ApiResponse(responseCode = "404", description = "Foto de produto não encontrada")
    })
    FotoProdutoResponse buscar(@Parameter(name = "ID do restaurante", example = "1", required = true)
                               Long restauranteId,
                               @Parameter(name = "ID do produto", example = "1", required = true)
                               Long produtoId);

    @Operation(summary = "Exclui a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Foto do produto excluída"),
            @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido"),
            @ApiResponse(responseCode = "404", description = "Foto de produto não encontrada")
    })
    void excluir(@Parameter(name = "ID do restaurante", example = "1", required = true)
                 Long restauranteId,
                 @Parameter(name = "ID do produto", example = "1", required = true)
                 Long produtoId);
}
