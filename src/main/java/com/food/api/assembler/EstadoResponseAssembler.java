package com.food.api.assembler;

import com.food.api.controller.EstadoController;
import com.food.api.model.response.EstadoResponse;
import com.food.domain.model.Estado;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EstadoResponseAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoResponse> {

    public EstadoResponseAssembler() {
        super(EstadoController.class, EstadoResponse.class);
    }

    @Override
    public EstadoResponse toModel(Estado estado) {
        EstadoResponse estadoResponse = new EstadoResponse(estado);

        estadoResponse.add(linkTo(
                methodOn(EstadoController.class).porId(estadoResponse.getId())
        ).withSelfRel());

        estadoResponse.add(linkTo(
                methodOn(EstadoController.class).listar()
        ).withRel("estados"));

        return estadoResponse;
    }

    @Override
    public CollectionModel<EstadoResponse> toCollectionModel(Iterable<? extends Estado> estados) {
        return super.toCollectionModel(estados)
                .add(linkTo(EstadoController.class).withSelfRel());
    }
}
