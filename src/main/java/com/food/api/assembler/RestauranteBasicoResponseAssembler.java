package com.food.api.assembler;

import com.food.api.controller.RestauranteController;
import com.food.api.model.response.RestauranteBasicoResponse;
import com.food.domain.model.Restaurante;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteBasicoResponseAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoResponse> {

    private final FoodLinks foodLinks;

    public RestauranteBasicoResponseAssembler(FoodLinks foodLinks) {
        super(RestauranteController.class, RestauranteBasicoResponse.class);
        this.foodLinks = foodLinks;
    }

    @Override
    public RestauranteBasicoResponse toModel(Restaurante restaurante) {
        return new RestauranteBasicoResponse(restaurante)
                .add(foodLinks.linkToRestaurante(restaurante.id()))
                .add(foodLinks.linkToRestaurantes("restaurantes"))
                .addCozinhaLink(foodLinks.linkToCozinha(restaurante.cozinha().id()));
    }

    @Override
    public CollectionModel<RestauranteBasicoResponse> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(foodLinks.linkToRestaurantes());
    }
}
