package com.food.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public record CozinhaRequest(@ApiModelProperty(example = "Brasileira", required = true)
                             @JsonProperty("nome")
                             @NotBlank
                             String nome) {
}
