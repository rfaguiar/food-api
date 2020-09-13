package com.food.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public record CozinhaIdRequest(@JsonProperty("id")
                               @NotNull
                               Long id) {
}
