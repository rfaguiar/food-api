package com.food.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public record SenhaRequest(@JsonProperty("senhaAtual")
                           @NotBlank
                           String senhaAtual,
                           @JsonProperty("novaSenha")
                           @NotBlank
                           String novaSenha) {
}
