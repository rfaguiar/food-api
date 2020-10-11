package com.food.api.openapi.controller;

import com.food.api.exceptionhandler.Problem;
import com.food.api.model.request.PedidoRequest;
import com.food.api.model.response.PedidoResponse;
import com.food.api.model.response.PedidoResumoResponse;
import com.food.domain.filter.PedidoFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import static com.food.config.OpenApiConfig.TAG_PEDIDO;

@Api(tags = TAG_PEDIDO)
public interface PedidoControllerOpenApi {

    @ApiOperation("Pesquisa os pedidos")
    PagedModel<PedidoResumoResponse> pesquisar(PedidoFilter filtro,
                                               Pageable pageable);

    @ApiOperation("Busca um pedido por código")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    PedidoResponse porId(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true)
                          String codigoPedido);

    @ApiOperation("Registra um pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pedido registrado"),
    })
    PedidoResponse adicionar(@ApiParam(name = "corpo", value = "Representação de um novo pedido", required = true)
                             PedidoRequest pedidoRequest);

    @ApiOperation("Confirmação de pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido confirmado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> confirmar(@ApiParam(value = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true)
                   String codigoPedido);

    @ApiOperation("Cancelamento de pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido cancelado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> cancelar(@ApiParam(value = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true)
                  String codigoPedido);

    @ApiOperation("Registrar entrega de pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Entrega de pedido registrada com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> entregar(@ApiParam(value = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true)
                  String codigoPedido);
}
