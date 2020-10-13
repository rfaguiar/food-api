package com.food.api.v2.openapi.controller;

import com.food.api.exceptionhandler.Problem;
import com.food.api.v2.model.request.CozinhaRequestV2;
import com.food.api.v2.model.response.CozinhaResponseV2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;

import static com.food.config.OpenApiConfig.TAG_COZINHA;

@Api(tags = TAG_COZINHA)
public interface CozinhaControllerV2OpenApi {

    @ApiOperation("Lista as cozinhas com paginação")
    PagedModel<CozinhaResponseV2> listar(@PageableDefault(size = 2) Pageable pageable);

    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaResponseV2 porId(@ApiParam(value = "ID de uma cozinha", example = "1", required = true)
                                    Long cozinhaId);

    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cozinha cadastrada"),
    })
    CozinhaResponseV2 adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cozinha", required = true)
                    CozinhaRequestV2 cozinhaV2);

    @ApiOperation("Atualiza uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha atualizada"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaResponseV2 atualizar(@ApiParam(value = "ID de uma cozinha", example = "1", required = true)
                                        Long cozinhaId,
                                @ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados",
                                        required = true)
                                        CozinhaRequestV2 cozinhaV2);

    @ApiOperation("Exclui uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cozinha excluída"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    void remover(@ApiParam(value = "ID de uma cozinha", example = "1", required = true)
                         Long cozinhaId);
}
