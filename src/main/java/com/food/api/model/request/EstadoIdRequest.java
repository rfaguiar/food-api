package com.food.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public record EstadoIdRequest(@JsonProperty("id")
                              @NotNull
                              Long id) {
}
