package com.food.api.assembler;

import com.food.api.controller.CidadeController;
import com.food.api.controller.EstadoController;
import com.food.api.model.response.CidadeResponse;
import com.food.domain.model.Cidade;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CidadeResponseAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeResponse> {

    public CidadeResponseAssembler() {
        super(CidadeController.class, CidadeResponse.class);
    }

    @Override
    public CidadeResponse toModel(Cidade cidade) {
        CidadeResponse cidadeResponse = new CidadeResponse(cidade);

        cidadeResponse.add(linkTo(
                methodOn(CidadeController.class).porId(cidadeResponse.getId())
        ).withSelfRel());

        cidadeResponse.add(linkTo(
                methodOn(CidadeController.class).listar()
        ).withRel("cidades"));

        cidadeResponse.getEstado().add(linkTo(methodOn(
                EstadoController.class).porId(cidadeResponse.getEstado().getId())
        ).withSelfRel());

        return cidadeResponse;
    }

    @Override
    public CollectionModel<CidadeResponse> toCollectionModel(Iterable<? extends Cidade> cidades) {
        return super.toCollectionModel(cidades)
                .add(linkTo(CidadeController.class).withSelfRel());
    }
}
