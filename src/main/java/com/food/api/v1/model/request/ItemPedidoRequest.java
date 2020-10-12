package com.food.api.v1.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public record ItemPedidoRequest(@ApiModelProperty(example = "1", required = true)
                                @JsonProperty("produtoId")
                                @NotNull
                                Long produtoId,
                                @ApiModelProperty(example = "2", required = true)
                                @JsonProperty("quantidade")
                                @NotNull
                                Integer quantidade,
                                @ApiModelProperty(example = "Menos picante, por favor")
                                @JsonProperty("observacao")
                                String  observacao) {
}
