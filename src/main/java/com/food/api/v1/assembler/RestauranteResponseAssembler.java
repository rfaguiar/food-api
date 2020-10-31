package com.food.api.v1.assembler;

import com.food.api.v1.controller.RestauranteController;
import com.food.api.v1.model.response.RestauranteResponse;
import com.food.config.FoodSecurity;
import com.food.domain.model.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteResponseAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteResponse> {

    private final FoodLinks foodLinks;
    private final FoodSecurity foodSecurity;

    @Autowired
    public RestauranteResponseAssembler(FoodLinks foodLinks, FoodSecurity foodSecurity) {
        super(RestauranteController.class, RestauranteResponse.class);
        this.foodLinks = foodLinks;
        this.foodSecurity = foodSecurity;
    }

    @Override
    public RestauranteResponse toModel(Restaurante restaurante) {
        RestauranteResponse restauranteResponse = new RestauranteResponse(restaurante)
                .add(foodLinks.linkToRestaurante(restaurante.getId()))
                .addRestauranteStatusLink(foodLinks, foodSecurity);

        if (foodSecurity.podeConsultarRestaurantes()) {
            restauranteResponse.add(foodLinks.linkToRestaurantes("restaurantes"));
        }

        if (foodSecurity.podeConsultarRestaurantes()) {
            restauranteResponse.add(foodLinks.linkToProdutos(restaurante.getId(), "produtos"));
        }

        if (foodSecurity.podeConsultarCozinhas()) {
            restauranteResponse.addCozinhaLink(foodLinks.linkToCozinha(restaurante.getCozinha().getId()));
        }

        if (foodSecurity.podeConsultarCidades()) {
            restauranteResponse.addCidadeEnderecoLink(foodLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
        }

        if (foodSecurity.podeConsultarRestaurantes()) {
            restauranteResponse.add(foodLinks.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));
        }

        if (foodSecurity.podeGerenciarCadastroRestaurantes()) {
            restauranteResponse.add(foodLinks.linkToResponsaveisRestaurante(restaurante.getId(), "responsaveis"));
        }
        return restauranteResponse;
    }

    @Override
    public CollectionModel<RestauranteResponse> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteResponse> restauranteResponses = super.toCollectionModel(entities);
        if (foodSecurity.podeConsultarRestaurantes()) {
            restauranteResponses.add(foodLinks.linkToRestaurantes());
        }
        return restauranteResponses;
    }
}
