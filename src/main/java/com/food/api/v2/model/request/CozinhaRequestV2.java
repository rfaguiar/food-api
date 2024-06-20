package com.food.api.v2.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

@Schema(defaultValue = "CozinhaRequest")
public record CozinhaRequestV2(@Schema(example = "Brasileira", required = true)
                             @JsonProperty("nomeCozinha")
                             @NotBlank
                             String nomeCozinha) {
}
