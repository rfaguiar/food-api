package com.food.api.v2.model.response;

import com.food.domain.model.Cozinha;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@ApiModel("CozinhaResponse")
@Relation(collectionRelation = "cozinhas")
public class CozinhaResponseV2 extends RepresentationModel<CozinhaResponseV2> {

    @ApiModelProperty(example = "1")
    private Long idCozinha;
    @ApiModelProperty(example = "Brasileira")
    private String nomeCozinha;

    public CozinhaResponseV2(Cozinha cozinha) {
        this(cozinha.id(), cozinha.nome());
    }

    public CozinhaResponseV2(Long idCozinha, String nomeCozinha) {
        this.idCozinha = idCozinha;
        this.nomeCozinha = nomeCozinha;
    }

    public Long getIdCozinha() {
        return idCozinha;
    }

    public String getNomeCozinha() {
        return nomeCozinha;
    }
}
