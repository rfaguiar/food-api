package com.food.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record ProdutoRequest(@JsonProperty("nome")
                             @NotBlank
                             String nome,
                             @JsonProperty("descricao")
                             @NotBlank
                             String descricao,
                             @JsonProperty("preco")
                             @NotNull
                             @PositiveOrZero
                             BigDecimal preco,
                             @JsonProperty("ativo")
                             @NotNull
                             Boolean ativo) {
}
