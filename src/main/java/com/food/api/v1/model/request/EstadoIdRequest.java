package com.food.api.v1.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

@Schema(defaultValue = "Estado", description = "Representa um estado")
public record EstadoIdRequest(@Schema(example = "1", required = true)
                              @JsonProperty("id")
                              @NotNull
                              Long id) {
}
