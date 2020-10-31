package com.food.api.v1.assembler;

import com.food.api.v1.controller.RestauranteProdutoController;
import com.food.api.v1.model.response.ProdutoResponse;
import com.food.config.FoodSecurity;
import com.food.domain.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoResponseAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoResponse> {

    private final FoodLinks foodLinks;
    private final FoodSecurity foodSecurity;

    @Autowired
    public ProdutoResponseAssembler(FoodLinks foodLinks, FoodSecurity foodSecurity) {
        super(RestauranteProdutoController.class, ProdutoResponse.class);
        this.foodLinks = foodLinks;
        this.foodSecurity = foodSecurity;
    }

    @Override
    public ProdutoResponse toModel(Produto produto) {
        ProdutoResponse produtoResponse = new ProdutoResponse(produto)
                .add(foodLinks.linkToProduto(produto.getRestaurante().getId(), produto.getId()));
        if (foodSecurity.podeConsultarRestaurantes()) {
            produtoResponse
                    .add(foodLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"))
                    .add(foodLinks.linkToFotoProduto(produto.getRestaurante().getId(), produto.getId(), "foto"));
        }
        return produtoResponse;
    }
}
