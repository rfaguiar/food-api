package com.food.api.v1.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

public record CozinhaRequest(@Schema(example = "Brasileira", required = true)
                             @JsonProperty("nome")
                             @NotBlank
                             String nome) {
}
