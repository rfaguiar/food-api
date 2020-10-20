package com.food.api.v1.controller;

import com.food.api.security.CheckSecurity;
import com.food.api.v1.assembler.FoodLinks;
import com.food.api.v1.assembler.FormaPagamentoResponseAssembler;
import com.food.api.v1.model.response.FormaPagamentoResponse;
import com.food.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.food.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    private final RestauranteService restauranteService;
    private final FormaPagamentoResponseAssembler formaPagamentoResponseAssembler;
    private final FoodLinks foodLinks;

    @Autowired
    public RestauranteFormaPagamentoController(RestauranteService restauranteService, FormaPagamentoResponseAssembler formaPagamentoResponseAssembler, FoodLinks foodLinks) {
        this.restauranteService = restauranteService;
        this.formaPagamentoResponseAssembler = formaPagamentoResponseAssembler;
        this.foodLinks = foodLinks;
    }

    @Override
    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public CollectionModel<FormaPagamentoResponse> listar(@PathVariable Long restauranteId) {
        CollectionModel<FormaPagamentoResponse> formaPagamentoResponses = formaPagamentoResponseAssembler
                .toCollectionModel(restauranteService.listarFormasPagamentoPorId(restauranteId))
                .removeLinks()
                .add(foodLinks.linkToRestauranteFormasPagamento(restauranteId))
                .add(foodLinks.linkToRestauranteFormasPagamentoAssociacao(restauranteId, "associar"));
        formaPagamentoResponses.forEach(f ->
                f.add(foodLinks.linkToRestauranteFormasPagamentoDessasociacao(restauranteId, f.getId(), "dessasociar")));
        return formaPagamentoResponses;
    }

    @Override
    @CheckSecurity.Restaurantes.PodeEditar
    @PutMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.associarFormaPagamentoPorId(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Restaurantes.PodeEditar
    @DeleteMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.desassociarFormaPagamentoPorId(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }
}
