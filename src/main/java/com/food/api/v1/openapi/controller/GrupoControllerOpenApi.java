package com.food.api.v1.openapi.controller;

import com.food.api.v1.model.request.GrupoRequest;
import com.food.api.v1.model.response.GrupoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

import static com.food.config.OpenApiConfig.TAG_GRUPO;

@Tag(name = TAG_GRUPO)
public interface GrupoControllerOpenApi {

    @Operation(summary = "Lista os grupos")
    CollectionModel<GrupoResponse> listar();

    @Operation(summary = "Busca um grupo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da grupo inválido"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    })
    GrupoResponse buscar(@Parameter(name = "ID de um grupo", example = "1", required = true) Long grupoId);

    @Operation(summary = "Cadastra um grupo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Grupo cadastrado"),
    })
    GrupoResponse cadastrar(@Parameter(name = "corpo", description = "Representação de um novo grupo", required = true) GrupoRequest grupo);

    @Operation(summary = "Atualiza um grupo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grupo atualizado"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    })
    GrupoResponse atualizar(@Parameter(name = "ID de um grupo", example = "1", required = true) Long grupoId,
                            @Parameter(name = "corpo", description = "Representação de um grupo com os novos dados", required = true) GrupoRequest dto);

    @Operation(summary = "Exclui um grupo por ID")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Grupo excluído"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    })
    void remover(@Parameter(name = "ID de um grupo", example = "1", required = true) Long grupoId);
}
