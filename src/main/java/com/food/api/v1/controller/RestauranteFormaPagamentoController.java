package com.food.api.v1.controller;

import com.food.api.security.CheckSecurity;
import com.food.api.v1.assembler.FoodLinks;
import com.food.api.v1.assembler.FormaPagamentoResponseAssembler;
import com.food.api.v1.model.response.FormaPagamentoResponse;
import com.food.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.food.config.FoodSecurity;
import com.food.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    private final RestauranteService restauranteService;
    private final FormaPagamentoResponseAssembler formaPagamentoResponseAssembler;
    private final FoodLinks foodLinks;
    private final FoodSecurity foodSecurity;

    @Autowired
    public RestauranteFormaPagamentoController(RestauranteService restauranteService, FormaPagamentoResponseAssembler formaPagamentoResponseAssembler, FoodLinks foodLinks, FoodSecurity foodSecurity) {
        this.restauranteService = restauranteService;
        this.formaPagamentoResponseAssembler = formaPagamentoResponseAssembler;
        this.foodLinks = foodLinks;
        this.foodSecurity = foodSecurity;
    }

    @Override
    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public CollectionModel<FormaPagamentoResponse> listar(@PathVariable Long restauranteId) {
        CollectionModel<FormaPagamentoResponse> formaPagamentoResponses = formaPagamentoResponseAssembler
                .toCollectionModel(restauranteService.listarFormasPagamentoPorId(restauranteId))
                .removeLinks()
                .add(foodLinks.linkToRestauranteFormasPagamento(restauranteId));

        if (foodSecurity.podeGerenciarFuncionamentoRestaurantes(restauranteId)) {
            formaPagamentoResponses.add(foodLinks.linkToRestauranteFormasPagamentoAssociacao(restauranteId, "associar"));
            formaPagamentoResponses.forEach(f ->
                    f.add(foodLinks.linkToRestauranteFormasPagamentoDessasociacao(restauranteId, f.getId(), "dessasociar")));
        }

        return formaPagamentoResponses;
    }

    @Override
    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.associarFormaPagamentoPorId(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @DeleteMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.desassociarFormaPagamentoPorId(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }
}
