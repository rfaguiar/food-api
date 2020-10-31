package com.food.api.v1.model.response;

import com.food.domain.model.Cidade;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
public class CidadeResponse extends RepresentationModel<CidadeResponse> {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "São Paulo")
    private String nome;
    private EstadoResponse estado;

    public CidadeResponse(Cidade cidade) {
        this(cidade.getId(), cidade.getNome(), new EstadoResponse(cidade.getEstado()));
    }

    public CidadeResponse(Long id, String nome, EstadoResponse estado) {
        this.id = id;
        this.nome = nome;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public EstadoResponse getEstado() {
        return estado;
    }

    public CidadeResponse addEstadoLink(Link linkToEstado) {
        estado.add(linkToEstado);
        return this;
    }
}
