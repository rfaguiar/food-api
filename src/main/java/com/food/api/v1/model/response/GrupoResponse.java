package com.food.api.v1.model.response;

import com.food.domain.model.Grupo;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "grupos")
public class GrupoResponse extends RepresentationModel<GrupoResponse> {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "Gerente")
    private String nome;

    public GrupoResponse(Grupo grupo) {
        this(grupo.getId(), grupo.getNome());
    }

    public GrupoResponse(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
