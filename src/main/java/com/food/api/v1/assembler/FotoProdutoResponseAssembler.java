package com.food.api.v1.assembler;

import com.food.api.v1.controller.RestauranteProdutoFotoController;
import com.food.api.v1.model.response.FotoProdutoResponse;
import com.food.config.FoodSecurity;
import com.food.domain.model.FotoProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoResponseAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoResponse> {

    private final FoodLinks foodLinks;
    private final FoodSecurity foodSecurity;

    @Autowired
    public FotoProdutoResponseAssembler(FoodLinks foodLinks, FoodSecurity foodSecurity) {
        super(RestauranteProdutoFotoController.class, FotoProdutoResponse.class);
        this.foodLinks = foodLinks;
        this.foodSecurity = foodSecurity;
    }

    @Override
    public FotoProdutoResponse toModel(FotoProduto produto) {
        FotoProdutoResponse fotoProdutoResponse = new FotoProdutoResponse(produto);
        // Quem pode consultar restaurantes, também pode consultar os produtos e fotos
        if (foodSecurity.podeConsultarRestaurantes()) {
            fotoProdutoResponse
                    .add(foodLinks.linkToFotoProduto(produto.produto().restaurante().id(), produto.id()))
                    .add(foodLinks.linkToProduto(produto.produto().restaurante().id(), produto.id(), "produto"));
        }
        return fotoProdutoResponse;
    }
}
