package com.food.api.controller;

import com.food.api.model.request.ProdutoRequest;
import com.food.api.model.response.ProdutoResponse;
import com.food.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.food.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

    private final ProdutoService produtoService;

    @Autowired
    public RestauranteProdutoController(ProdutoService restauranteService) {
        this.produtoService = restauranteService;
    }

    @Override
    @GetMapping
    public List<ProdutoResponse> listar(@PathVariable Long restauranteId, @RequestParam(required = false) boolean incluirInativos) {
        if (incluirInativos) {
            return produtoService.listarProdutosPorId(restauranteId);
        }
        return produtoService.listarProdutosPorIdEAtivos(restauranteId);
    }

    @Override
    @GetMapping("/{produtoId}")
    public ProdutoResponse porId(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        return produtoService.buscarPorId(restauranteId, produtoId);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponse adicionar(@PathVariable Long restauranteId,
                                     @RequestBody @Valid ProdutoRequest produto) {
        return produtoService.adicionar(restauranteId, produto);
    }

    @Override
    @PutMapping("/{produtoId}")
    public ProdutoResponse atualizar(@PathVariable Long restauranteId,
                                     @PathVariable Long produtoId,
                                     @RequestBody @Valid ProdutoRequest produto) {
        return produtoService.atualizar(restauranteId, produtoId, produto);
    }
}
