package com.food.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Estado;
import com.food.service.validation.EstadoIdGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record EstadoDto(@JsonProperty("id")
                        @NotNull(groups = EstadoIdGroup.class)
                        Long id,
                        @JsonProperty("nome")
                        @NotBlank
                        String nome) {
    public EstadoDto(Estado estado) {
        this(estado.id(), estado.nome());
    }
}
