package com.food.api.v1.assembler;

import com.food.api.v1.controller.RestauranteProdutoController;
import com.food.api.v1.model.response.ProdutoResponse;
import com.food.domain.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoResponseAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoResponse> {

    private final FoodLinks foodLinks;

    @Autowired
    public ProdutoResponseAssembler(FoodLinks foodLinks) {
        super(RestauranteProdutoController.class, ProdutoResponse.class);
        this.foodLinks = foodLinks;
    }

    @Override
    public ProdutoResponse toModel(Produto produto) {
        return new ProdutoResponse(produto)
                .add(foodLinks.linkToProduto(produto.restaurante().id(), produto.id()))
                .add(foodLinks.linkToProdutos(produto.restaurante().id(),"produtos"))
                .add(foodLinks.linkToFotoProduto(produto.restaurante().id(), produto.id(), "foto"));
    }
}