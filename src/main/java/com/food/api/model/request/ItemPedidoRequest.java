package com.food.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public record ItemPedidoRequest(@JsonProperty("produtoId")
                                @NotNull
                                Long produtoId,
                                @JsonProperty("quantidade")
                                @NotNull
                                Integer quantidade,
                                @JsonProperty("observacao")
                                String  observacao) {
}
