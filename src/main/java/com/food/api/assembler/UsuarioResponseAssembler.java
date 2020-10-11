package com.food.api.assembler;

import com.food.api.controller.UsuarioController;
import com.food.api.model.response.UsuarioResponse;
import com.food.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class UsuarioResponseAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioResponse> {

    private final FoodLinks foodLinks;

    @Autowired
    public UsuarioResponseAssembler(FoodLinks foodLinks) {
        super(UsuarioController.class, UsuarioResponse.class);
        this.foodLinks = foodLinks;
    }

    @Override
    public UsuarioResponse toModel(Usuario usuario) {
        return new UsuarioResponse(usuario)
                .add(foodLinks.linkToUsuario(usuario.id()))
                .add(foodLinks.linkToUsuarios("usuarios"))
                .add(foodLinks.linkToGruposUsuario(usuario.id(),"grupos-usuario"));
    }

    @Override
    public CollectionModel<UsuarioResponse> toCollectionModel(Iterable<? extends Usuario> usuarios) {
        return super.toCollectionModel(usuarios)
                .add(linkTo(UsuarioController.class).withSelfRel());
    }
}
