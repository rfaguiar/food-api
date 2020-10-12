package com.food.api.assembler;

import com.food.api.model.response.PermissaoResponse;
import com.food.domain.model.Permissao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PermissaoResponseAssembler implements RepresentationModelAssembler<Permissao, PermissaoResponse> {

    private final FoodLinks foodLinks;

    @Autowired
    public PermissaoResponseAssembler(FoodLinks foodLinks) {
        this.foodLinks = foodLinks;
    }

    @Override
    public PermissaoResponse toModel(Permissao permissao) {
        return new PermissaoResponse(permissao);
    }

    @Override
    public CollectionModel<PermissaoResponse> toCollectionModel(Iterable<? extends Permissao> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(foodLinks.linkToPermissoes());
    }
}
