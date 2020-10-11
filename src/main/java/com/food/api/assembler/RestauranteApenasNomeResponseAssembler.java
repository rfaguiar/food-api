package com.food.api.assembler;

import com.food.api.controller.RestauranteController;
import com.food.api.model.response.RestauranteApenasNomeResponse;
import com.food.domain.model.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteApenasNomeResponseAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeResponse> {

    private final FoodLinks foodLinks;

    @Autowired
    public RestauranteApenasNomeResponseAssembler(FoodLinks foodLinks) {
        super(RestauranteController.class, RestauranteApenasNomeResponse.class);
        this.foodLinks = foodLinks;
    }

    @Override
    public RestauranteApenasNomeResponse toModel(Restaurante restaurante) {
        return new RestauranteApenasNomeResponse(restaurante)
                .add(foodLinks.linkToRestaurante(restaurante.id()))
                .add(foodLinks.linkToRestaurantes("restaurantes"));
    }

    @Override
    public CollectionModel<RestauranteApenasNomeResponse> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(foodLinks.linkToRestaurantes());
    }
}
