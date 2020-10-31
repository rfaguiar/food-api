package com.food.api.v3.assembler;

import com.food.api.v3.controller.CidadeControllerV3;
import com.food.api.v3.model.response.CidadeResponseV3;
import com.food.domain.model.Cidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeResponseAssemblerV3 extends RepresentationModelAssemblerSupport<Cidade, CidadeResponseV3> {

    private final FoodLinksV3 foodLinks;

    @Autowired
    public CidadeResponseAssemblerV3(FoodLinksV3 foodLinks) {
        super(CidadeControllerV3.class, CidadeResponseV3.class);
        this.foodLinks = foodLinks;
    }

    @Override
    public CidadeResponseV3 toModel(Cidade cidade) {
        return new CidadeResponseV3(cidade)
                .add(foodLinks.linkToCidade(cidade.getId()))
                .add(foodLinks.linkToCidades("cidades"));
    }

    @Override
    public CollectionModel<CidadeResponseV3> toCollectionModel(Iterable<? extends Cidade> cidades) {
        return super.toCollectionModel(cidades)
                .add(foodLinks.linkToCidades());
    }
}
