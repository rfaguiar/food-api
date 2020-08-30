package com.food.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.food.domain.model.Cozinha;

@JsonRootName("cozinha")
public record CozinhaDTO (@JsonProperty("id") Long id,
                          @JsonProperty("nome") String nome) {

    public CozinhaDTO (Cozinha cozinha) {
        this(cozinha.id(), cozinha.nome());
    }
}
