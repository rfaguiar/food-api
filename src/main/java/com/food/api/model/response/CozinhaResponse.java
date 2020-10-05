package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Cozinha;
import io.swagger.annotations.ApiModelProperty;

public record CozinhaResponse(@ApiModelProperty(example = "1")
                              @JsonProperty("id")
                              Long id,
                              @ApiModelProperty(example = "Brasileira")
                              @JsonProperty("nome")
                              String nome) {

    public CozinhaResponse(Cozinha cozinha) {
        this(cozinha.id(), cozinha.nome());
    }
}
