package com.food.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public record FormaPagamentoIdRequest(@ApiModelProperty(example = "1", required = true)
                                    @JsonProperty("id")
                                    @NotNull
                                    Long id) {
}
