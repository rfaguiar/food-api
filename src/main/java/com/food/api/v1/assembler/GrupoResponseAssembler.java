package com.food.api.v1.assembler;

import com.food.api.v1.controller.GrupoController;
import com.food.api.v1.model.response.GrupoResponse;
import com.food.config.FoodSecurity;
import com.food.domain.model.Grupo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GrupoResponseAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoResponse> {

    private final FoodLinks foodLinks;
    private final FoodSecurity foodSecurity;

    @Autowired
    public GrupoResponseAssembler(FoodLinks foodLinks, FoodSecurity foodSecurity) {
        super(GrupoController.class, GrupoResponse.class);
        this.foodLinks = foodLinks;
        this.foodSecurity = foodSecurity;
    }

    @Override
    public GrupoResponse toModel(Grupo grupo) {
        GrupoResponse grupoResponse = new GrupoResponse(grupo)
                .add(foodLinks.linkToGrupo(grupo.getId()));

        if (foodSecurity.podeConsultarUsuariosGruposPermissoes()) {
            grupoResponse
                    .add(foodLinks.linkToGrupos("grupos"))
                    .add(foodLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
        }

        return grupoResponse;
    }

    @Override
    public CollectionModel<GrupoResponse> toCollectionModel(Iterable<? extends Grupo> grupos) {
        CollectionModel<GrupoResponse> grupoResponses = super.toCollectionModel(grupos);

        if (foodSecurity.podeConsultarUsuariosGruposPermissoes()) {
            grupoResponses.add(foodLinks.linkToGrupos());
        }
        return grupoResponses;
    }
}
