package com.food.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Cozinha;

public record CozinhaDTO (@JsonProperty("id") Long id,
                          @JsonProperty("nome") String nome) {

    public CozinhaDTO (Cozinha cozinha) {
        this(cozinha.id(), cozinha.nome());
    }
}
