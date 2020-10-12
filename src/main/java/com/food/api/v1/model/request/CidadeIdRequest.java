package com.food.api.v1.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public record CidadeIdRequest(@ApiModelProperty(example = "1", required = true)
                              @JsonProperty("id") Long id) {
}
