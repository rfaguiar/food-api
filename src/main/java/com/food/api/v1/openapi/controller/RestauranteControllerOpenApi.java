package com.food.api.v1.openapi.controller;

import com.food.api.v1.model.request.RestauranteRequest;
import com.food.api.v1.model.response.RestauranteApenasNomeResponse;
import com.food.api.v1.model.response.RestauranteBasicoResponse;
import com.food.api.v1.model.response.RestauranteResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static com.food.config.OpenApiConfig.TAG_RESTRAURANTE;

@Tag(name = TAG_RESTRAURANTE)
public interface RestauranteControllerOpenApi {

    @Operation(summary = "Lista restaurantes")
    @Parameters({
            @Parameter(description = "Nome da projeção de pedidos",
                    name = "projecao")
    })
    CollectionModel<RestauranteBasicoResponse> listar();

    @Hidden
    @Operation(summary = "Lista restaurantes", hidden = true)
    CollectionModel<RestauranteApenasNomeResponse> listarApenasNomes();

    @Operation(summary = "Busca um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do restaurante inválido"),
            @ApiResponse(responseCode ="404", description = "Restaurante não encontrado")
    })
    RestauranteResponse porId(
            @Parameter(name = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);

    @Operation(summary = "Cadastra um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode ="201", description = "Restaurante cadastrado"),
    })
    RestauranteResponse adicionar(
            @Parameter(name = "corpo", description = "Representação de um novo restaurante", required = true)
            RestauranteRequest restaurante);

    @Operation(summary = "Atualiza um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode ="200", description = "Restaurante atualizado"),
            @ApiResponse(responseCode ="404", description = "Restaurante não encontrado")
    })
    RestauranteResponse atualizar(
            @Parameter(name = "ID de um restaurante", example = "1", required = true)
            Long restauranteId,
            @Parameter(name = "corpo", description = "Representação de um restaurante com os novos dados", required = true)
            RestauranteRequest restaurante);

    @Operation(summary = "Ativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode ="204", description = "Restaurante ativado com sucesso"),
            @ApiResponse(responseCode ="404", description = "Restaurante não encontrado")
    })
    ResponseEntity<Void> ativar(@Parameter(name = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @Operation(summary = "Inativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode ="204", description = "Restaurante inativado com sucesso"),
            @ApiResponse(responseCode ="404", description = "Restaurante não encontrado")
    })
    ResponseEntity<Void> inativar(@Parameter(name = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    RestauranteResponse atualizarParcial(Long restauranteId,
                                         Map<String, Object> campos);

    @Operation(summary = "Abre um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode ="204", description = "Restaurante aberto com sucesso"),
            @ApiResponse(responseCode ="404", description = "Restaurante não encontrado")
    })
    ResponseEntity<Void> abrir(@Parameter(name = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @Operation(summary = "Fecha um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode ="204", description = "Restaurante fechado com sucesso"),
            @ApiResponse(responseCode ="404", description = "Restaurante não encontrado")
    })
    ResponseEntity<Void> fechar(@Parameter(name = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @Operation(summary = "Ativa múltiplos restaurantes")
    @ApiResponses({
            @ApiResponse(responseCode ="204", description = "Restaurantes ativados com sucesso")
    })
    ResponseEntity<Void> ativarMultiplos(@Parameter(name = "corpo", description = "IDs de restaurantes", required = true) List<Long> restaurantesIds);

    @Operation(summary = "Inativa múltiplos restaurantes")
    @ApiResponses({
            @ApiResponse(responseCode ="204", description = "Restaurantes ativados com sucesso")
    })
    ResponseEntity<Void> inativarMultiplos(@Parameter(name = "corpo", description = "IDs de restaurantes", required = true) List<Long> restaurantesIds);
}
