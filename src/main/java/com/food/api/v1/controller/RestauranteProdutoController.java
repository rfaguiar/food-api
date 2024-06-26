package com.food.api.v1.controller;

import com.food.api.security.CheckSecurity;
import com.food.api.v1.assembler.FoodLinks;
import com.food.api.v1.assembler.ProdutoResponseAssembler;
import com.food.api.v1.model.request.ProdutoRequest;
import com.food.api.v1.model.response.ProdutoResponse;
import com.food.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.food.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/restaurantes/{restauranteId}/produtos")
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
    @CheckSecurity.Restaurantes.PodeConsultar
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
    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping("/{produtoId}")
    public ProdutoResponse porId(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        return produtoResponseAssembler.toModel(produtoService.buscarPorId(restauranteId, produtoId));
    }

    @Override
    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponse adicionar(@PathVariable Long restauranteId,
                                     @RequestBody @Valid ProdutoRequest produto) {
        return produtoResponseAssembler.toModel(produtoService.adicionar(restauranteId, produto));
    }

    @Override
    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("/{produtoId}")
    public ProdutoResponse atualizar(@PathVariable Long restauranteId,
                                     @PathVariable Long produtoId,
                                     @RequestBody @Valid ProdutoRequest produto) {
        return produtoResponseAssembler.toModel(produtoService.atualizar(restauranteId, produtoId, produto));
    }
}
