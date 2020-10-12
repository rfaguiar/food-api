package com.food.api.v1.assembler;

import com.food.api.v1.controller.GrupoController;
import com.food.api.v1.model.response.GrupoResponse;
import com.food.domain.model.Grupo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GrupoResponseAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoResponse> {

    private final FoodLinks foodLinks;

    @Autowired
    public GrupoResponseAssembler(FoodLinks foodLinks) {
        super(GrupoController.class, GrupoResponse.class);
        this.foodLinks = foodLinks;
    }

    @Override
    public GrupoResponse toModel(Grupo grupo) {
        return new GrupoResponse(grupo)
                .add(foodLinks.linkToGrupo(grupo.id()))
                .add(foodLinks.linkToGrupos("grupos"))
                .add(foodLinks.linkToGrupoPermissoes(grupo.id(), "permissoes"));
    }

    @Override
    public CollectionModel<GrupoResponse> toCollectionModel(Iterable<? extends Grupo> grupos) {
        return super.toCollectionModel(grupos)
                .add(foodLinks.linkToGrupos());
    }
}
