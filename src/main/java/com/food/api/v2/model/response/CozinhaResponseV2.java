package com.food.api.v2.model.response;

import com.food.domain.model.Cozinha;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Schema(defaultValue = "CozinhaResponse")
@Relation(collectionRelation = "cozinhas")
public class CozinhaResponseV2 extends RepresentationModel<CozinhaResponseV2> {

    @Schema(example = "1")
    private Long idCozinha;
    @Schema(example = "Brasileira")
    private String nomeCozinha;

    public CozinhaResponseV2(Cozinha cozinha) {
        this(cozinha.getId(), cozinha.getNome());
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
