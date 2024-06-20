package com.food.api.v1.openapi.controller;

import com.food.api.v1.model.request.CidadeRequest;
import com.food.api.v1.model.response.CidadeResponse;
import com.food.config.OpenApiConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@Tag(name = OpenApiConfig.TAG_CIDADE)
public interface CidadeControllerOpenApi {

    @Operation(summary = "Lista as cidades")
    CollectionModel<CidadeResponse> listar();

    @Operation(summary = "Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da cidade inválido"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada")
    })
    CidadeResponse porId(@Parameter(name = "ID de uma cidade", example = "1", required = true) Long cidadeId);

    @Operation(summary = "Cadastra uma cidade")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "representação de uma nova cidade")
    })
    CidadeResponse adicionar(@Parameter(name = "corpo", description = "Representação de uma nova cidade", required = true)
                                    CidadeRequest cidade);

    @Operation(summary = "Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cidade atualizada"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada")
    })
    CidadeResponse atualizar(@Parameter(name = "ID de uma cidade", example = "1", required = true)
                             Long cidadeId,
                             @Parameter(name = "corpo", description = "Representação de uma nova cidade com os novos dados")
                             CidadeRequest cidade);

    @Operation(summary = "Exclui uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cidade excluída"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada")
    })
    void remover(@Parameter(name = "ID de uma cidade", example = "1", required = true)
                 Long cidadeId);
}
