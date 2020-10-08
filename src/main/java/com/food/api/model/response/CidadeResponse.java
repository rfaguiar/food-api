package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Cidade;
import io.swagger.annotations.ApiModelProperty;

public record CidadeResponse(@ApiModelProperty(example = "1")
                             @JsonProperty("id")
                             Long id,
                             @ApiModelProperty(example = "São Paulo")
                             @JsonProperty("nome")
                             String nome,
                             @JsonProperty("estado")
                             EstadoResponse estado) {

    public CidadeResponse(Cidade cidade) {
        this(cidade.id(), cidade.nome(), new EstadoResponse(cidade.estado()));
    }
}
