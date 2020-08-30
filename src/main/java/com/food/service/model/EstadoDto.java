package com.food.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Estado;

public record EstadoDto(@JsonProperty("id") Long id, @JsonProperty("nome") String nome) {
    public EstadoDto(Estado estado) {
        this(estado.id(), estado.nome());
    }
}
