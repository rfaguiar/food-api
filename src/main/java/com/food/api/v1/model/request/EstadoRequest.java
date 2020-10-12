package com.food.api.v1.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public record EstadoRequest(@ApiModelProperty(example = "Minas Gerais", required = true)
                            @JsonProperty("nome")
                            @NotBlank
                            String nome) {
}
