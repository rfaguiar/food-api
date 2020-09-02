package com.food.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.food.domain.model.Cozinha;

@JsonRootName("cozinha")
public record CozinhaDto(@JsonProperty("id") Long id,
                         @JsonProperty("nome") String nome) {

    public CozinhaDto(Cozinha cozinha) {
        this(cozinha.id(), cozinha.nome());
    }
}