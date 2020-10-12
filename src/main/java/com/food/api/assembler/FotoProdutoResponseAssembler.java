package com.food.api.assembler;

import com.food.api.controller.RestauranteProdutoFotoController;
import com.food.api.model.response.FotoProdutoResponse;
import com.food.domain.model.FotoProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoResponseAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoResponse> {

    private final FoodLinks foodLinks;

    @Autowired
    public FotoProdutoResponseAssembler(FoodLinks foodLinks) {
        super(RestauranteProdutoFotoController.class, FotoProdutoResponse.class);
        this.foodLinks = foodLinks;
    }

    @Override
    public FotoProdutoResponse toModel(FotoProduto produto) {
        return new FotoProdutoResponse(produto)
                .add(foodLinks.linkToFotoProduto(produto.produto().restaurante().id(), produto.id()))
                .add(foodLinks.linkToProduto(produto.produto().restaurante().id(), produto.id(),"produto"));
    }
}
