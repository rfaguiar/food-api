package com.food.api.model.response;

import com.food.domain.model.Grupo;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "grupos")
public class GrupoResponse extends RepresentationModel<GrupoResponse> {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Gerente")
    private String nome;

    public GrupoResponse(Grupo grupo) {
        this(grupo.id(), grupo.nome());
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
