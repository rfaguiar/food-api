package com.food.api.v1.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

public record GrupoRequest(@Schema(example = "Gerente", required = true)
                           @JsonProperty("nome")
                           @NotBlank String nome) {
}
