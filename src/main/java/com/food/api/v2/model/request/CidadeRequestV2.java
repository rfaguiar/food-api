package com.food.api.v2.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "Cidade", description = "Representa uma cidade")
public record CidadeRequestV2(
        @ApiModelProperty(example = "São Paulo", required = true)
        @JsonProperty("nomeCidade")
        @NotBlank
        String nomeCidade,
        @ApiModelProperty(example = "1", required = true)
        @JsonProperty("idEstado")
        @NotNull
        Long idEstado) {
}
