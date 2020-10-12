package com.food.api.v2.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public record CozinhaRequestV2(@ApiModelProperty(example = "Brasileira", required = true)
                             @JsonProperty("nomeCozinha")
                             @NotBlank
                             String nomeCozinha) {
}
