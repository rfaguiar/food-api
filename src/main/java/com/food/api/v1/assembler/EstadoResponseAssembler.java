package com.food.api.v1.assembler;

import com.food.api.v1.controller.EstadoController;
import com.food.api.v1.model.response.EstadoResponse;
import com.food.config.FoodSecurity;
import com.food.domain.model.Estado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class EstadoResponseAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoResponse> {

    private final FoodLinks foodLinks;
    private final FoodSecurity foodSecurity;

    @Autowired
    public EstadoResponseAssembler(FoodLinks foodLinks, FoodSecurity foodSecurity) {
        super(EstadoController.class, EstadoResponse.class);
        this.foodLinks = foodLinks;
        this.foodSecurity = foodSecurity;
    }

    @Override
    public EstadoResponse toModel(Estado estado) {
        EstadoResponse estadoResponse = new EstadoResponse(estado)
                .add(foodLinks.linkToEstado(estado.getId()));

        if (foodSecurity.podeConsultarEstados()) {
            estadoResponse.add(foodLinks.linkToEstados("estados"));
        }

        return estadoResponse;
    }

    @Override
    public CollectionModel<EstadoResponse> toCollectionModel(Iterable<? extends Estado> estados) {
        CollectionModel<EstadoResponse> estadoResponses = super.toCollectionModel(estados);
        if (foodSecurity.podeConsultarEstados()) {
            estadoResponses.add(foodLinks.linkToEstados());
        }
        return estadoResponses;
    }
}
