package com.food.api.v1.assembler;

import com.food.api.v1.controller.RestauranteController;
import com.food.api.v1.model.response.RestauranteBasicoResponse;
import com.food.config.FoodSecurity;
import com.food.domain.model.Restaurante;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteBasicoResponseAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoResponse> {

    private final FoodLinks foodLinks;
    private final FoodSecurity foodSecurity;

    public RestauranteBasicoResponseAssembler(FoodLinks foodLinks, FoodSecurity foodSecurity) {
        super(RestauranteController.class, RestauranteBasicoResponse.class);
        this.foodLinks = foodLinks;
        this.foodSecurity = foodSecurity;
    }

    @Override
    public RestauranteBasicoResponse toModel(Restaurante restaurante) {
        RestauranteBasicoResponse restauranteBasicoResponse = new RestauranteBasicoResponse(restaurante)
                .add(foodLinks.linkToRestaurante(restaurante.id()));

        if (foodSecurity.podeConsultarRestaurantes()) {
            restauranteBasicoResponse.add(foodLinks.linkToRestaurantes("restaurantes"));
        }

        if (foodSecurity.podeConsultarCozinhas()) {
            restauranteBasicoResponse.addCozinhaLink(foodLinks.linkToCozinha(restaurante.cozinha().id()));
        }
        return restauranteBasicoResponse;
    }

    @Override
    public CollectionModel<RestauranteBasicoResponse> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteBasicoResponse> restauranteBasicoResponses = super.toCollectionModel(entities);
        if (foodSecurity.podeConsultarRestaurantes()) {
            restauranteBasicoResponses.add(foodLinks.linkToRestaurantes());
        }
        return restauranteBasicoResponses;
    }
}
