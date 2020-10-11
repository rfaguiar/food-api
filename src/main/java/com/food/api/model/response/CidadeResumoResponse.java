package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.food.domain.model.Cidade;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Optional;

@Relation("cidades")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CidadeResumoResponse extends RepresentationModel<CidadeResumoResponse> {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Uberlândia")
    private String nome;
    @ApiModelProperty(example = "Minas Gerais")
    private String estado;

    public CidadeResumoResponse(Cidade cidade) {
        this(cidade.id(), cidade.nome(), cidade.estado().nome());
    }

    public CidadeResumoResponse(Optional<Cidade> cidade) {
        this(cidade.map(Cidade::id).orElse(null),
                cidade.map(Cidade::nome).orElse(null),
                cidade.map(c -> c.estado().nome()).orElse(null));
    }

    public CidadeResumoResponse(Long id, String nome, String estado) {
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

    public String getEstado() {
        return estado;
    }
}
