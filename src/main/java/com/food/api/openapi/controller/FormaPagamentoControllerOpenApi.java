package com.food.api.openapi.controller;

import com.food.api.exceptionhandler.Problem;
import com.food.api.model.request.FormaPagamentoRequest;
import com.food.api.model.response.FormaPagamentoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.food.config.OpenApiConfig.TAG_FORMA_PAGAMENTO;

@Api(tags = TAG_FORMA_PAGAMENTO)
public interface FormaPagamentoControllerOpenApi {

    @ApiOperation("Lista as formas de pagamento")
    List<FormaPagamentoResponse> listar();

    @ApiOperation("Busca uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    ResponseEntity<FormaPagamentoResponse> buscar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
                                                  Long formaPagamentoId);

    @ApiOperation("Cadastra uma forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Forma de pagamento cadastrada"),
    })
    FormaPagamentoResponse cadastrar(@ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento", required = true)
                                     FormaPagamentoRequest formaPagamentoDto);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    FormaPagamentoResponse atualizar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
                                     Long formaPagamentoId,
                                     @ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com os novos dados", required = true)
                                     FormaPagamentoRequest formaPagamentoDto);

    @ApiOperation("Exclui uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Forma de pagamento excluída"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    void remover(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);
}
