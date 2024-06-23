package com.food.api.v1.openapi.controller;

import com.food.api.exceptionhandler.Problem;
import com.food.api.v1.model.request.ProdutoRequest;
import com.food.api.v1.model.response.ProdutoResponse;
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

import static com.food.config.OpenApiConfig.TAG_PRODUTOS;

@SecurityRequirement(name = OpenApiConfig.SECURITY_AUTH)
@Tag(name = TAG_PRODUTOS)
public interface RestauranteProdutoControllerOpenApi {

    @Operation(summary = "Lista os produtos de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "ID do restaurante inválido"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Restaurante não encontrado")
    })
    CollectionModel<ProdutoResponse> listar(@Parameter(description = "ID do restaurante", example = "1", required = true)
                                 Long restauranteId,
                                            @Parameter(description = "Indica se deve ou não incluir produtos inativos no resultado da listagem",
                                    example = "false")
                                 Boolean incluirInativos);

    @Operation(summary = "Busca um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "ID do restaurante ou produto inválido"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Produto de restaurante não encontrado")
    })
    ProdutoResponse porId(@Parameter(description = "ID do restaurante", example = "1", required = true)
                           Long restauranteId,
                          @Parameter(description = "ID do produto", example = "1", required = true)
                           Long produtoId);

    @Operation(summary = "Cadastra um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto cadastrado"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Restaurante não encontrado")
    })
    ProdutoResponse adicionar(@Parameter(description = "ID do restaurante", example = "1", required = true)
                              Long restauranteId,
                              @Parameter( description = "Representação de um novo produto", required = true)
                              ProdutoRequest produto);

    @Operation(summary = "Atualiza um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Produto de restaurante não encontrado")
    })
    ProdutoResponse atualizar(@Parameter(description = "ID do restaurante", example = "1", required = true)
                              Long restauranteId,
                              @Parameter(description = "ID do produto", example = "1", required = true)
                              Long produtoId,
                              @Parameter( description = "Representação de um produto com os novos dados",
                                      required = true)
                              ProdutoRequest produto);
}
