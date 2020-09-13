package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Cozinha;

public record CozinhaResponse(@JsonProperty("id")
                              Long id,
                              @JsonProperty("nome")
                              String nome) {

    public CozinhaResponse(Cozinha cozinha) {
        this(cozinha.id(), cozinha.nome());
    }
}
