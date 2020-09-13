package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Cidade;
import com.food.service.model.EstadoDto;

public record CidadeResponse(@JsonProperty("id")
                             Long id,
                             @JsonProperty("nome")
                             String nome,
                             @JsonProperty("estado")
                             EstadoDto estado) {

    public CidadeResponse(Cidade cidade) {
        this(cidade.id(), cidade.nome(), new EstadoDto(cidade.estado()));
    }
}
