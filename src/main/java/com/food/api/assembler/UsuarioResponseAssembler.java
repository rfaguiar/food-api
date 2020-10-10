package com.food.api.assembler;

import com.food.api.controller.UsuarioController;
import com.food.api.controller.UsuarioGrupoController;
import com.food.api.model.response.UsuarioResponse;
import com.food.domain.model.Usuario;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioResponseAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioResponse> {

    public UsuarioResponseAssembler() {
        super(UsuarioController.class, UsuarioResponse.class);
    }

    @Override
    public UsuarioResponse toModel(Usuario usuario) {
        UsuarioResponse usuarioResponse = new UsuarioResponse(usuario);

        usuarioResponse.add(linkTo(
                methodOn(UsuarioController.class).buscar(usuarioResponse.getId())
        ).withSelfRel());

        usuarioResponse.add(linkTo(methodOn(UsuarioGrupoController.class)
                .listar(usuario.id())).withRel("grupos-usuario"));

        return usuarioResponse;
    }

    @Override
    public CollectionModel<UsuarioResponse> toCollectionModel(Iterable<? extends Usuario> usuarios) {
        return super.toCollectionModel(usuarios)
                .add(linkTo(UsuarioController.class).withSelfRel());
    }
}
