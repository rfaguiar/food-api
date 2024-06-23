package com.food.api.v1.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public record CozinhaIdRequest(@Schema(example = "1", required = true)
                               @JsonProperty("id")
                               @NotNull
                               Long id) {
}
