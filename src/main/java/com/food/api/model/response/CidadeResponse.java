package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Cidade;

public record CidadeResponse(@JsonProperty("id")
                             Long id,
                             @JsonProperty("nome")
                             String nome,
                             @JsonProperty("estado")
                             EstadoResponse estado) {

    public CidadeResponse(Cidade cidade) {
        this(cidade.id(), cidade.nome(), new EstadoResponse(cidade.estado()));
    }
}
