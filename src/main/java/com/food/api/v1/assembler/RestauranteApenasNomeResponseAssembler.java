package com.food.api.v1.assembler;

import com.food.api.v1.controller.RestauranteController;
import com.food.api.v1.model.response.RestauranteApenasNomeResponse;
import com.food.config.FoodSecurity;
import com.food.domain.model.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteApenasNomeResponseAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeResponse> {

    private final FoodLinks foodLinks;
    private final FoodSecurity foodSecurity;

    @Autowired
    public RestauranteApenasNomeResponseAssembler(FoodLinks foodLinks, FoodSecurity foodSecurity) {
        super(RestauranteController.class, RestauranteApenasNomeResponse.class);
        this.foodLinks = foodLinks;
        this.foodSecurity = foodSecurity;
    }

    @Override
    public RestauranteApenasNomeResponse toModel(Restaurante restaurante) {
        RestauranteApenasNomeResponse restaurantes = new RestauranteApenasNomeResponse(restaurante)
                .add(foodLinks.linkToRestaurante(restaurante.id()));
        if (foodSecurity.podeConsultarRestaurantes()) {
            restaurantes.add(foodLinks.linkToRestaurantes("restaurantes"));
        }
        return restaurantes;
    }

    @Override
    public CollectionModel<RestauranteApenasNomeResponse> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteApenasNomeResponse> restauranteApenasNomeResponses = super.toCollectionModel(entities);
        if (foodSecurity.podeConsultarRestaurantes()) {
            restauranteApenasNomeResponses.add(foodLinks.linkToRestaurantes());
        }
        return restauranteApenasNomeResponses;
    }
}
