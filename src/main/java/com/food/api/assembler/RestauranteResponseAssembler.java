package com.food.api.assembler;

import com.food.api.controller.RestauranteController;
import com.food.api.model.response.RestauranteResponse;
import com.food.domain.model.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteResponseAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteResponse> {

    private final FoodLinks foodLinks;

    @Autowired
    public RestauranteResponseAssembler(FoodLinks foodLinks) {
        super(RestauranteController.class, RestauranteResponse.class);
        this.foodLinks = foodLinks;
    }

    @Override
    public RestauranteResponse toModel(Restaurante restaurante) {
        return new RestauranteResponse(restaurante)
                .add(foodLinks.linkToRestaurante(restaurante.id()))
                .add(foodLinks.linkToRestaurantes("restaurantes"))
                .add(foodLinks.linkToProdutos(restaurante.id(), "produtos"))
                .add(foodLinks.linkToRestauranteFormasPagamento(restaurante.id(), "formas-pagamento"))
                .add(foodLinks.linkToResponsaveisRestaurante(restaurante.id(), "responsaveis"))
                .addCozinhaLink(foodLinks.linkToCozinha(restaurante.cozinha().id()))
                .addCidadeEnderecoLink(foodLinks.linkToCidade(restaurante.endereco().cidade().id()))
                .addRestauranteStatusLink(foodLinks);
    }

    @Override
    public CollectionModel<RestauranteResponse> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(foodLinks.linkToRestaurantes());
    }
}
