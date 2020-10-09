package com.food.api.model.response;

import com.food.domain.model.Cidade;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.RepresentationModel;

public class CidadeResponse extends RepresentationModel<CidadeResponse> {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "São Paulo")
    private String nome;
    private EstadoResponse estado;

    public CidadeResponse(Cidade cidade) {
        this(cidade.id(), cidade.nome(), new EstadoResponse(cidade.estado()));
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
}
