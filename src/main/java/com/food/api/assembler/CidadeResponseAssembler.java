package com.food.api.assembler;

import com.food.api.controller.CidadeController;
import com.food.api.model.response.CidadeResponse;
import com.food.domain.model.Cidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeResponseAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeResponse> {

    private final FoodLinks foodLinks;

    @Autowired
    public CidadeResponseAssembler(FoodLinks foodLinks) {
        super(CidadeController.class, CidadeResponse.class);
        this.foodLinks = foodLinks;
    }

    @Override
    public CidadeResponse toModel(Cidade cidade) {
        return new CidadeResponse(cidade)
                .add(foodLinks.linkToCidade(cidade.id()))
                .add(foodLinks.linkToCidades("cidades"))
                .addEstadoLink(foodLinks.linkToEstado(cidade.estado().id()));
    }

    @Override
    public CollectionModel<CidadeResponse> toCollectionModel(Iterable<? extends Cidade> cidades) {
        return super.toCollectionModel(cidades)
                .add(foodLinks.linkToCidades());
    }
}
