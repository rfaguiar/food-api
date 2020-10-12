package com.food.api.v1.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public record FormaPagamentoRequest(@ApiModelProperty(example = "Cartão de crédito", required = true)
                                    @JsonProperty("descricao")
                                    @NotBlank
                                    String descricao) {
}
