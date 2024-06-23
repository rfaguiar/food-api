package com.food.api.v1.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public record ItemPedidoRequest(@Schema(example = "1", required = true)
                                @JsonProperty("produtoId")
                                @NotNull
                                Long produtoId,
                                @Schema(example = "2", required = true)
                                @JsonProperty("quantidade")
                                @NotNull
                                Integer quantidade,
                                @Schema(example = "Menos picante, por favor")
                                @JsonProperty("observacao")
                                String  observacao) {
}
