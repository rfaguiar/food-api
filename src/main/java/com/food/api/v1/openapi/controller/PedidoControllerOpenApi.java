package com.food.api.v1.openapi.controller;

import com.food.api.v1.model.request.PedidoRequest;
import com.food.api.v1.model.response.PedidoResponse;
import com.food.api.v1.model.response.PedidoResumoResponse;
import com.food.domain.filter.PedidoFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import static com.food.config.OpenApiConfig.TAG_PEDIDO;

@Tag(name = TAG_PEDIDO)
public interface PedidoControllerOpenApi {

    @Operation(summary = "Pesquisa os pedidos")
    PagedModel<PedidoResumoResponse> pesquisar(PedidoFilter filtro,
                                               Pageable pageable);

    @Operation(summary = "Busca um pedido por código")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    PedidoResponse porId(@Parameter(name = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true)
                          String codigoPedido);

    @Operation(summary = "Registra um pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido registrado"),
    })
    PedidoResponse adicionar(@Parameter(name = "corpo", description = "Representação de um novo pedido", required = true)
                             PedidoRequest pedidoRequest);

    @Operation(summary = "Confirmação de pedido")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Pedido confirmado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    ResponseEntity<Void> confirmar(@Parameter(name = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true)
                   String codigoPedido);

    @Operation(summary = "Cancelamento de pedido")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Pedido cancelado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    ResponseEntity<Void> cancelar(@Parameter(name = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true)
                  String codigoPedido);

    @Operation(summary = "Registrar entrega de pedido")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Entrega de pedido registrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    ResponseEntity<Void> entregar(@Parameter(name = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true)
                  String codigoPedido);
}
