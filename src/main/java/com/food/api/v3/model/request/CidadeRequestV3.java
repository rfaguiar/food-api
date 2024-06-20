package com.food.api.v3.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(defaultValue = "CidadeRequest")
public record CidadeRequestV3(
        @Schema(example = "São Paulo", required = true)
        @JsonProperty("nomeCidade")
        @NotBlank
        String nomeCidade,
        @Schema(example = "1", required = true)
        @JsonProperty("idEstado")
        @NotNull
        Long idEstado) {
}
