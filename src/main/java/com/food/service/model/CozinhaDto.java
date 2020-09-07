package com.food.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.food.domain.model.Cozinha;
import com.food.service.validation.CozinhaIdGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonRootName("cozinha")
public record CozinhaDto(@JsonProperty("id")
                         @NotNull(groups = CozinhaIdGroup.class)
                         Long id,
                         @JsonProperty("nome")
                         @NotBlank
                         String nome) {

    public CozinhaDto(Cozinha cozinha) {
        this(cozinha.id(), cozinha.nome());
    }
}
