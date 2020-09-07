package com.food.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.food.domain.model.Cozinha;
import com.food.service.validation.CadastroCozinhaGroup;
import com.food.service.validation.CadastroRestauranteGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonRootName("cozinha")
public record CozinhaDto(@JsonProperty("id")
                         @NotNull(groups = CadastroRestauranteGroup.class)
                         Long id,
                         @JsonProperty("nome")
                         @NotBlank(groups = CadastroCozinhaGroup.class)
                         String nome) {

    public CozinhaDto(Cozinha cozinha) {
        this(cozinha.id(), cozinha.nome());
    }
}
