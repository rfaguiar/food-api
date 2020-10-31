package com.food.api.v3.openapi.controller;

import com.food.api.exceptionhandler.Problem;
import com.food.api.v3.model.request.CidadeRequestV3;
import com.food.api.v3.model.response.CidadeResponseV3;
import com.food.config.OpenApiConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.CollectionModel;

@Api(tags = OpenApiConfig.TAG_CIDADE)
public interface CidadeControllerV3OpenApi {

    @ApiOperation("Lista as cidades")
    CollectionModel<CidadeResponseV3> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeResponseV3 porId(@ApiParam(value = "ID de uma cidade", example = "1", required = true)
                                   Long cidadeId);

    @ApiOperation("Cadastra uma cidade")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cidade cadastrada"),
    })
    CidadeResponseV3 adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true)
                                       CidadeRequestV3 cidadeV2);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeResponseV3 atualizar(@ApiParam(value = "ID de uma cidade", example = "1", required = true)
                                       Long cidadeId,
                               @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados", required = true)
                                       CidadeRequestV3 cidadeV2);

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    void remover(@ApiParam(value = "ID de uma cidade", example = "1", required = true)
                         Long cidadeId);
}
