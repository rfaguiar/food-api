package com.food.api.controller;

import com.food.api.assembler.FoodLinks;
import com.food.api.assembler.ProdutoResponseAssembler;
import com.food.api.model.request.ProdutoRequest;
import com.food.api.model.response.ProdutoResponse;
import com.food.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.food.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

    private final ProdutoService produtoService;
    private final ProdutoResponseAssembler produtoResponseAssembler;
    private final FoodLinks foodLinks;

    @Autowired
    public RestauranteProdutoController(ProdutoService restauranteService, ProdutoResponseAssembler produtoResponseAssembler, FoodLinks foodLinks) {
        this.produtoService = restauranteService;
        this.produtoResponseAssembler = produtoResponseAssembler;
        this.foodLinks = foodLinks;
    }

    @Override
    @GetMapping
    public CollectionModel<ProdutoResponse> listar(@PathVariable Long restauranteId,
                                                   @RequestParam(required = false, defaultValue = "false")
                                                   Boolean incluirInativos) {
        var linkToProdutos = foodLinks.linkToProdutos(restauranteId);
        if (incluirInativos) {
            return produtoResponseAssembler.toCollectionModel(produtoService.listarProdutosPorId(restauranteId))
                    .add(linkToProdutos);
        }
        return produtoResponseAssembler.toCollectionModel(produtoService.listarProdutosPorIdEAtivos(restauranteId))
                .add(linkToProdutos);
    }

    @Override
    @GetMapping("/{produtoId}")
    public ProdutoResponse porId(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        return produtoResponseAssembler.toModel(produtoService.buscarPorId(restauranteId, produtoId));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponse adicionar(@PathVariable Long restauranteId,
                                     @RequestBody @Valid ProdutoRequest produto) {
        return produtoResponseAssembler.toModel(produtoService.adicionar(restauranteId, produto));
    }

    @Override
    @PutMapping("/{produtoId}")
    public ProdutoResponse atualizar(@PathVariable Long restauranteId,
                                     @PathVariable Long produtoId,
                                     @RequestBody @Valid ProdutoRequest produto) {
        return produtoResponseAssembler.toModel(produtoService.atualizar(restauranteId, produtoId, produto));
    }
}
