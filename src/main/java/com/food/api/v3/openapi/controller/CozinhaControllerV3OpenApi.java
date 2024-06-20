package com.food.api.v3.openapi.controller;

import com.food.api.v3.model.request.CozinhaRequestV3;
import com.food.api.v3.model.response.CozinhaResponseV3;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;

import static com.food.config.OpenApiConfig.TAG_COZINHA;

@Tag(name = TAG_COZINHA)
public interface CozinhaControllerV3OpenApi {

    @Operation(summary = "Lista as cozinhas com paginação")
    PagedModel<CozinhaResponseV3> listar(@PageableDefault(size = 2) Pageable pageable);

    @Operation(summary = "Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da cozinha inválido"),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada")
    })
    CozinhaResponseV3 porId(@Parameter(name = "ID de uma cozinha", example = "1", required = true)
                                    Long cozinhaId);

    @Operation(summary = "Cadastra uma cozinha")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cozinha cadastrada"),
    })
    CozinhaResponseV3 adicionar(
            @Parameter(name = "corpo", description = "Representação de uma nova cozinha", required = true)
                    CozinhaRequestV3 cozinhaV2);

    @Operation(summary = "Atualiza uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cozinha atualizada"),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada")
    })
    CozinhaResponseV3 atualizar(@Parameter(name = "ID de uma cozinha", example = "1", required = true)
                                        Long cozinhaId,
                                @Parameter(name = "corpo", description = "Representação de uma cozinha com os novos dados",
                                        required = true)
                                        CozinhaRequestV3 cozinhaV2);

    @Operation(summary = "Exclui uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Cozinha excluída"),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada")
    })
    void remover(@Parameter(name = "ID de uma cozinha", example = "1", required = true)
                         Long cozinhaId);
}
