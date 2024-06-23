package com.food.api.v1.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

public record SenhaRequest(@Schema(example = "123", required = true)
                           @JsonProperty("senhaAtual")
                           @NotBlank
                           String senhaAtual,
                           @Schema(example = "123", required = true)
                           @JsonProperty("novaSenha")
                           @NotBlank
                           String novaSenha) {
}
