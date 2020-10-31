package com.food.api.v1.assembler;

import com.food.api.v1.controller.CidadeController;
import com.food.api.v1.model.response.CidadeResponse;
import com.food.config.FoodSecurity;
import com.food.domain.model.Cidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeResponseAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeResponse> {

    private final FoodLinks foodLinks;
    private final FoodSecurity foodSecurity;

    @Autowired
    public CidadeResponseAssembler(FoodLinks foodLinks, FoodSecurity foodSecurity) {
        super(CidadeController.class, CidadeResponse.class);
        this.foodLinks = foodLinks;
        this.foodSecurity = foodSecurity;
    }

    @Override
    public CidadeResponse toModel(Cidade cidade) {
        CidadeResponse cidadeResponse = new CidadeResponse(cidade)
                .add(foodLinks.linkToCidade(cidade.getId()));
        if (foodSecurity.podeConsultarCidades()) {
            cidadeResponse.add(foodLinks.linkToCidades("cidades"));
        }

        if (foodSecurity.podeConsultarEstados()) {
            cidadeResponse.addEstadoLink(foodLinks.linkToEstado(cidade.getEstado().getId()));
        }
        return cidadeResponse;
    }

    @Override
    public CollectionModel<CidadeResponse> toCollectionModel(Iterable<? extends Cidade> cidades) {
        CollectionModel<CidadeResponse> cidadeResponses = super.toCollectionModel(cidades);
        if (foodSecurity.podeConsultarCidades()) {
            cidadeResponses.add(foodLinks.linkToCidades());
        }
        return cidadeResponses;
    }
}
