package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Grupo;

public record GrupoResponse(@JsonProperty("id") Long id,
                            @JsonProperty("nome") String nome) {

    public GrupoResponse(Grupo grupo) {
        this(grupo.id(), grupo.nome());
    }
}
