package com.food.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Cidade;

public record CidadeDto(@JsonProperty("id") Long id,
                        @JsonProperty("nome") String nome,
                        @JsonProperty("estado") EstadoDto estado) {

    public CidadeDto(Cidade cidade) {
        this(cidade.id(), cidade.nome(), new EstadoDto(cidade.estado()));
    }
}
