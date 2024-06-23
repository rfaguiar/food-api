package com.food.api.v1.openapi.controller;

import com.food.api.exceptionhandler.Problem;
import com.food.api.v1.model.request.EstadoRequest;
import com.food.api.v1.model.response.EstadoResponse;
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

import static com.food.config.OpenApiConfig.TAG_ESTADOS;

@SecurityRequirement(name = OpenApiConfig.SECURITY_AUTH)
@Tag(name = TAG_ESTADOS)
public interface EstadoControllerOpenApi {

    @Operation(summary = "Lista os estados")
    CollectionModel<EstadoResponse> listar();

    @Operation(summary = "Busca um estado por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "ID do estado inválido"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Estado não encontrado")
    })
    EstadoResponse porId(@Parameter(description = "ID de um estado", example = "1", required = true) Long estadoId);

    @Operation(summary = "Cadastra um estado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Estado cadastrado"),
    })
    EstadoResponse adicionar(@Parameter( description = "Representação de um novo estado", required = true) EstadoRequest estado);

    @Operation(summary = "Atualiza um estado por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado atualizado"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Estado não encontrado")
    })
    EstadoResponse atualizar(
            @Parameter(description = "ID de um estado", example = "1", required = true)
            Long estadoId,
            @Parameter( description = "Representação de um estado com os novos dados", required = true)
            EstadoRequest estado);

    @Operation(summary = "Exclui um estado por ID")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Estado excluído"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Estado não encontrado")
    })
    void remover(@Parameter(description = "ID de um estado", example = "1", required = true) Long estadoId);
}
