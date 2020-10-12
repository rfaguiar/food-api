package com.food.api.v1.assembler;

import com.food.api.v1.controller.EstadoController;
import com.food.api.v1.model.response.EstadoResponse;
import com.food.domain.model.Estado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class EstadoResponseAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoResponse> {

    private final FoodLinks foodLinks;

    @Autowired
    public EstadoResponseAssembler(FoodLinks foodLinks) {
        super(EstadoController.class, EstadoResponse.class);
        this.foodLinks = foodLinks;
    }

    @Override
    public EstadoResponse toModel(Estado estado) {
       return new EstadoResponse(estado)
               .add(foodLinks.linkToEstado(estado.id()))
               .add(foodLinks.linkToEstados("estados"));
    }

    @Override
    public CollectionModel<EstadoResponse> toCollectionModel(Iterable<? extends Estado> estados) {
        return super.toCollectionModel(estados)
                .add(foodLinks.linkToEstados());
    }
}
