package com.food.api.v2.model.response;

import com.food.domain.model.Cidade;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Schema(defaultValue = "CidadeResponse")
@Relation(collectionRelation = "cidades")
public class CidadeResponseV2 extends RepresentationModel<CidadeResponseV2> {

    @Schema(example = "1")
    private Long idCidade;
    @Schema(example = "São Paulo")
    private String nomeCidade;
    private Long idEstado;
    private String nomeEstado;

    public CidadeResponseV2(Cidade cidade) {
        this(cidade.getId(), cidade.getNome(), cidade.getEstado().getId(), cidade.getEstado().getNome());
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
