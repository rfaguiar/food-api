package com.food.api.v2.assembler;

import com.food.api.v2.controller.CidadeControllerV2;
import com.food.api.v2.model.response.CidadeResponseV2;
import com.food.domain.model.Cidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeResponseAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeResponseV2> {

    private final FoodLinksV2 foodLinks;

    @Autowired
    public CidadeResponseAssemblerV2(FoodLinksV2 foodLinks) {
        super(CidadeControllerV2.class, CidadeResponseV2.class);
        this.foodLinks = foodLinks;
    }

    @Override
    public CidadeResponseV2 toModel(Cidade cidade) {
        return new CidadeResponseV2(cidade)
                .add(foodLinks.linkToCidade(cidade.id()))
                .add(foodLinks.linkToCidades("cidades"));
    }

    @Override
    public CollectionModel<CidadeResponseV2> toCollectionModel(Iterable<? extends Cidade> cidades) {
        return super.toCollectionModel(cidades)
                .add(foodLinks.linkToCidades());
    }
}
