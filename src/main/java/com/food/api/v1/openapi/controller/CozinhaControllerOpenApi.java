package com.food.api.v1.openapi.controller;

import com.food.api.v1.model.request.CozinhaRequest;
import com.food.api.v1.model.response.CozinhaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import static com.food.config.OpenApiConfig.TAG_COZINHA;

@Tag(name = TAG_COZINHA)
public interface CozinhaControllerOpenApi {

    @Operation(summary = "Lista as cozinhas com paginação")
    PagedModel<CozinhaResponse> listar(Pageable pageable);

    @Operation(summary = "Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da cozinha inválido"),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada")
    })
    CozinhaResponse porId(@Parameter(name = "ID de uma cozinha", example = "1", required = true) Long cozinhaId);

    @Operation(summary = "Cadastra uma cozinha")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cozinha cadastrada"),
    })
    CozinhaResponse adicionar(@Parameter(name = "corpo", description = "Representação de uma nova cozinha", required = true)
                                      CozinhaRequest cozinha);

    @Operation(summary = "Atualiza uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cozinha atualizada"),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada")
    })
    CozinhaResponse atualizar(@Parameter(name = "ID de uma cozinha", example = "1", required = true)
                              Long cozinhaId,
                              @Parameter(name = "corpo", description = "Representação de uma cozinha com os novos dados")
                              CozinhaRequest cozinha);

    @Operation(summary = "Exclui uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Cozinha excluída"),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada")
    })
    void remover(@Parameter(name = "ID de uma cozinha", example = "1", required = true) Long cozinhaId);
}
