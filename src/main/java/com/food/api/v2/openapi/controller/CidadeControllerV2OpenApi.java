package com.food.api.v2.openapi.controller;

import com.food.api.exceptionhandler.Problem;
import com.food.api.v2.model.request.CidadeRequestV2;
import com.food.api.v2.model.response.CidadeResponseV2;
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

@SecurityRequirement(name = OpenApiConfig.SECURITY_AUTH)
@Tag(name = OpenApiConfig.TAG_CIDADE)
public interface CidadeControllerV2OpenApi {

    @Operation(summary = "Lista as cidades")
    CollectionModel<CidadeResponseV2> listar();

    @Operation(summary = "Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "ID da cidade inválido"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Cidade não encontrada")
    })
    CidadeResponseV2 porId(@Parameter(description = "ID de uma cidade", example = "1", required = true)
                                   Long cidadeId);

    @Operation(summary = "Cadastra uma cidade")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cidade cadastrada"),
    })
    CidadeResponseV2 adicionar(@Parameter( description  = "Representação de uma nova cidade", required = true)
                                       CidadeRequestV2 cidadeV2);

    @Operation(summary = "Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cidade atualizada"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Cidade não encontrada")
    })
    CidadeResponseV2 atualizar(@Parameter(description = "ID de uma cidade", example = "1", required = true)
                                       Long cidadeId,
                               @Parameter( description  = "Representação de uma cidade com os novos dados", required = true)
                                       CidadeRequestV2 cidadeV2);

    @Operation(summary = "Exclui uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Cidade excluída"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Cidade não encontrada")
    })
    void remover(@Parameter(description = "ID de uma cidade", example = "1", required = true)
                         Long cidadeId);
}
