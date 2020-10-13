package com.food.api.v2.model.response;

import com.food.domain.model.Cidade;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@ApiModel("CidadeResponse")
@Relation(collectionRelation = "cidades")
public class CidadeResponseV2 extends RepresentationModel<CidadeResponseV2> {

    @ApiModelProperty(example = "1")
    private Long idCidade;
    @ApiModelProperty(example = "São Paulo")
    private String nomeCidade;
    private Long idEstado;
    private String nomeEstado;

    public CidadeResponseV2(Cidade cidade) {
        this(cidade.id(), cidade.nome(), cidade.estado().id(), cidade.estado().nome());
    }

    public CidadeResponseV2(Long idCidade, String nomeCidade, Long idEstado, String nomeEstado) {
        this.idCidade = idCidade;
        this.nomeCidade = nomeCidade;
        this.idEstado = idEstado;
        this.nomeEstado = nomeEstado;
    }

    public Long getIdCidade() {
        return idCidade;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public String getNomeEstado() {
        return nomeEstado;
    }
}
