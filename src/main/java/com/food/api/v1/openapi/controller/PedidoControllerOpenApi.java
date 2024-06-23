package com.food.api.v1.openapi.controller;

import com.food.api.exceptionhandler.Problem;
import com.food.api.openapi.PageableParameter;
import com.food.api.v1.model.request.PedidoRequest;
import com.food.api.v1.model.response.PedidoResponse;
import com.food.api.v1.model.response.PedidoResumoResponse;
import com.food.config.OpenApiConfig;
import com.food.domain.filter.PedidoFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import static com.food.config.OpenApiConfig.TAG_PEDIDO;

@SecurityRequirement(name = OpenApiConfig.SECURITY_AUTH)
@Tag(name = TAG_PEDIDO)
public interface PedidoControllerOpenApi {

    @Operation(summary = "Pesquisa os pedidos")
    @PageableParameter
    PagedModel<PedidoResumoResponse> pesquisar(PedidoFilter filtro,
                                               @Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Busca um pedido por código")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Pedido não encontrado")
    })
    PedidoResponse porId(@Parameter(description = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true)
                          String codigoPedido);

    @Operation(summary = "Registra um pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido registrado"),
    })
    PedidoResponse adicionar(@Parameter( description = "Representação de um novo pedido", required = true)
                             PedidoRequest pedidoRequest);

    @Operation(summary = "Confirmação de pedido")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Pedido confirmado com sucesso"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Pedido não encontrado")
    })
    ResponseEntity<Void> confirmar(@Parameter(description = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true)
                   String codigoPedido);

    @Operation(summary = "Cancelamento de pedido")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Pedido cancelado com sucesso"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Pedido não encontrado")
    })
    ResponseEntity<Void> cancelar(@Parameter(description = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true)
                  String codigoPedido);

    @Operation(summary = "Registrar entrega de pedido")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Entrega de pedido registrada com sucesso"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Pedido não encontrado")
    })
    ResponseEntity<Void> entregar(@Parameter(description = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true)
                  String codigoPedido);
}
