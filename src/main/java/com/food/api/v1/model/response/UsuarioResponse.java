package com.food.api.v1.model.response;

import com.food.domain.model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "usuarios")
public class UsuarioResponse extends RepresentationModel<UsuarioResponse> {

    @Schema(example = "1")
    Long id;
    @Schema(example = "João da Silva")
    String nome;
    @Schema(example = "joao.ger@food.com.br")
    String email;

    public UsuarioResponse(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    public UsuarioResponse(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}
