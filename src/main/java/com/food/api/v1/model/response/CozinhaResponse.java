package com.food.api.v1.model.response;

import com.food.domain.model.Cozinha;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cozinhas")
public class CozinhaResponse extends RepresentationModel<CozinhaResponse> {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Brasileira")
    private String nome;

    public CozinhaResponse(Cozinha cozinha) {
        this(cozinha.getId(), cozinha.getNome());
    }

    public CozinhaResponse(Long id, String nome) {
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
