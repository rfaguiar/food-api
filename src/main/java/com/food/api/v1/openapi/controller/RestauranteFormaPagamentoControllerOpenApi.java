package com.food.api.v1.openapi.controller;

import com.food.api.exceptionhandler.Problem;
import com.food.api.v1.model.response.FormaPagamentoResponse;
import com.food.config.OpenApiConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import static com.food.config.OpenApiConfig.TAG_RESTRAURANTE;

@SecurityRequirement(name = OpenApiConfig.SECURITY_AUTH)
@Tag(name = TAG_RESTRAURANTE)
public interface RestauranteFormaPagamentoControllerOpenApi {

    @Operation(summary = "Lista as formas de pagamento associadas a restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Restaurante não encontrado")
    })
    CollectionModel<FormaPagamentoResponse> listar(@Parameter(description = "ID do restaurante", example = "1", required = true)
                                                Long restauranteId);

    @Operation(summary = "Associação de restaurante com forma de pagamento")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Associação realizada com sucesso"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Restaurante ou forma de pagamento não encontrado")
    })
    ResponseEntity<Void> associar(@Parameter(description = "ID do restaurante", example = "1", required = true)
                          Long restauranteId,
                  @Parameter(description = "ID da forma de pagamento", example = "1", required = true)
                          Long formaPagamentoId);

    @Operation(summary = "Desassociação de restaurante com forma de pagamento")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Desassociação realizada com sucesso"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Restaurante ou forma de pagamento não encontrado")
    })
    ResponseEntity<Void> desassociar(@Parameter(description = "ID do restaurante", example = "1", required = true)
                             Long restauranteId,
                                     @Parameter(description = "ID da forma de pagamento", example = "1", required = true)
                             Long formaPagamentoId);
}
