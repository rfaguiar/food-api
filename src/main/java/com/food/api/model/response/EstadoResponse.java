package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Estado;

public record EstadoResponse(@JsonProperty("id")
                             Long id,
                             @JsonProperty("nome")
                             String nome) {
    public EstadoResponse(Estado estado) {
        this(estado.id(), estado.nome());
    }
}
