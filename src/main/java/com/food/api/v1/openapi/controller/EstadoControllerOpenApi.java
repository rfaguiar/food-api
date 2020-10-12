package com.food.api.v1.openapi.controller;

import com.food.api.exceptionhandler.Problem;
import com.food.api.v1.model.request.EstadoRequest;
import com.food.api.v1.model.response.EstadoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.CollectionModel;

import static com.food.config.OpenApiConfig.TAG_ESTADOS;

@Api(tags = TAG_ESTADOS)
public interface EstadoControllerOpenApi {

    @ApiOperation("Lista os estados")
    CollectionModel<EstadoResponse> listar();

    @ApiOperation("Busca um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do estado inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    EstadoResponse porId(@ApiParam(value = "ID de um estado", example = "1", required = true) Long estadoId);

    @ApiOperation("Cadastra um estado")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Estado cadastrado"),
    })
    EstadoResponse adicionar(@ApiParam(name = "corpo", value = "Representação de um novo estado", required = true) EstadoRequest estado);

    @ApiOperation("Atualiza um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estado atualizado"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    EstadoResponse atualizar(
            @ApiParam(value = "ID de um estado", example = "1", required = true)
            Long estadoId,
            @ApiParam(name = "corpo", value = "Representação de um estado com os novos dados", required = true)
            EstadoRequest estado);

    @ApiOperation("Exclui um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Estado excluído"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    void remover(@ApiParam(value = "ID de um estado", example = "1", required = true) Long estadoId);
}
