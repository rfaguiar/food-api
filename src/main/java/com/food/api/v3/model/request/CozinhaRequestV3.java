package com.food.api.v3.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

@ApiModel("CozinhaRequest")
public record CozinhaRequestV3(@ApiModelProperty(example = "Brasileira", required = true)
                             @JsonProperty("nomeCozinha")
                             @NotBlank
                             String nomeCozinha) {
}
