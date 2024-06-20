package com.food.api.v1.openapi.controller;

import com.food.api.v1.model.request.FormaPagamentoRequest;
import com.food.api.v1.model.response.FormaPagamentoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import static com.food.config.OpenApiConfig.TAG_FORMA_PAGAMENTO;

@Tag(name = TAG_FORMA_PAGAMENTO)
public interface FormaPagamentoControllerOpenApi {

    @Operation(summary = "Lista as formas de pagamento")
    CollectionModel<FormaPagamentoResponse> listar();

    @Operation(summary = "Busca uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da forma de pagamento inválido"),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada")
    })
    ResponseEntity<FormaPagamentoResponse> porId(@Parameter(name = "ID de uma forma de pagamento", example = "1", required = true)
                                                  Long formaPagamentoId);

    @Operation(summary = "Cadastra uma forma de pagamento")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Forma de pagamento cadastrada"),
    })
    FormaPagamentoResponse cadastrar(@Parameter(name = "corpo", description = "Representação de uma nova forma de pagamento", required = true)
                                     FormaPagamentoRequest formaPagamentoDto);

    @Operation(summary = "Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Forma de pagamento atualizada"),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada")
    })
    FormaPagamentoResponse atualizar(@Parameter(name = "ID de uma forma de pagamento", example = "1", required = true)
                                     Long formaPagamentoId,
                                     @Parameter(name = "corpo", description = "Representação de uma forma de pagamento com os novos dados", required = true)
                                     FormaPagamentoRequest formaPagamentoDto);

    @Operation(summary = "Exclui uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Forma de pagamento excluída"),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada")
    })
    void remover(@Parameter(name = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);
}
