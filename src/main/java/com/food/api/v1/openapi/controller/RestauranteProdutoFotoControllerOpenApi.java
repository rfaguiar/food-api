package com.food.api.v1.openapi.controller;

import com.food.api.exceptionhandler.Problem;
import com.food.api.v1.model.response.FotoProdutoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.multipart.MultipartFile;

import static com.food.config.OpenApiConfig.TAG_PRODUTOS;

@Api(tags = TAG_PRODUTOS)
public interface RestauranteProdutoFotoControllerOpenApi {


    @ApiOperation("Atualiza a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Foto do produto atualizada"),
            @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problem.class)
    })
    FotoProdutoResponse atualizarFoto(@ApiParam(value = "ID do restaurante", example = "1", required = true)
                                      Long restauranteId,
                                      @ApiParam(value = "ID do produto", example = "1", required = true)
                                      Long produtoId,
                                      @ApiParam(value = "Descrição da foto do produto", required = true)
                                      String descricao,
                                      @ApiParam(value = "Arquivo da foto do produto (máximo 500KB, apenas JPG e PNG)", required = true)
                                      MultipartFile arquivo);

    @ApiOperation(value = "Busca a foto do produto de um restaurante",
            produces = "application/json, image/jpeg, image/png")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Foto de produto não encontrada", response = Problem.class)
    })
    FotoProdutoResponse buscar(@ApiParam(value = "ID do restaurante", example = "1", required = true)
                               Long restauranteId,
                               @ApiParam(value = "ID do produto", example = "1", required = true)
                               Long produtoId);

    @ApiOperation("Exclui a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Foto do produto excluída"),
            @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Foto de produto não encontrada", response = Problem.class)
    })
    void excluir(@ApiParam(value = "ID do restaurante", example = "1", required = true)
                 Long restauranteId,
                 @ApiParam(value = "ID do produto", example = "1", required = true)
                 Long produtoId);
}
