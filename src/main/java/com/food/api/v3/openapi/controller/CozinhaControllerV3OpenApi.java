package com.food.api.v3.openapi.controller;

import com.food.api.exceptionhandler.Problem;
import com.food.api.openapi.PageableParameter;
import com.food.api.v3.model.request.CozinhaRequestV3;
import com.food.api.v3.model.response.CozinhaResponseV3;
import com.food.config.OpenApiConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;

import static com.food.config.OpenApiConfig.TAG_COZINHA;

@SecurityRequirement(name = OpenApiConfig.SECURITY_AUTH)
@Tag(name = TAG_COZINHA)
public interface CozinhaControllerV3OpenApi {

    @Operation(summary = "Lista as cozinhas com paginação")
    @PageableParameter
    PagedModel<CozinhaResponseV3> listar(@PageableDefault(size = 2) @Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "ID da cozinha inválido"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Cozinha não encontrada")
    })
    CozinhaResponseV3 porId(@Parameter(description = "ID de uma cozinha", example = "1", required = true)
                                    Long cozinhaId);

    @Operation(summary = "Cadastra uma cozinha")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cozinha cadastrada"),
    })
    CozinhaResponseV3 adicionar(
            @Parameter( description = "Representação de uma nova cozinha", required = true)
                    CozinhaRequestV3 cozinhaV2);

    @Operation(summary = "Atualiza uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cozinha atualizada"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Cozinha não encontrada")
    })
    CozinhaResponseV3 atualizar(@Parameter(description = "ID de uma cozinha", example = "1", required = true)
                                        Long cozinhaId,
                                @Parameter( description = "Representação de uma cozinha com os novos dados",
                                        required = true)
                                        CozinhaRequestV3 cozinhaV2);

    @Operation(summary = "Exclui uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Cozinha excluída"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Cozinha não encontrada")
    })
    void remover(@Parameter(description = "ID de uma cozinha", example = "1", required = true)
                         Long cozinhaId);
}
