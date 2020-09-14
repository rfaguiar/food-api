package com.food.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public record GrupoRequest(@JsonProperty("nome") @NotBlank String nome) {
}
