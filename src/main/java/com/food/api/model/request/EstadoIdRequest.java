package com.food.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel(value = "Estado", description = "Representa um estado")
public record EstadoIdRequest(@ApiModelProperty(example = "1", required = true)
                              @JsonProperty("id")
                              @NotNull
                              Long id) {
}
