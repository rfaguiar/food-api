package com.food.api.v3.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("CidadeRequest")
public record CidadeRequestV3(
        @ApiModelProperty(example = "São Paulo", required = true)
        @JsonProperty("nomeCidade")
        @NotBlank
        String nomeCidade,
        @ApiModelProperty(example = "1", required = true)
        @JsonProperty("idEstado")
        @NotNull
        Long idEstado) {
}
