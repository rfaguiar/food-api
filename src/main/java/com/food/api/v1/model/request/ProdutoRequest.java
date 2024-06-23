package com.food.api.v1.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record ProdutoRequest(@Schema(example = "Espetinho de Cupim", required = true)
                             @JsonProperty("nome")
                             @NotBlank
                             String nome,
                             @Schema(example = "Acompanha farinha, mandioca e vinagrete", required = true)
                             @JsonProperty("descricao")
                             @NotBlank
                             String descricao,
                             @Schema(example = "12.50", required = true)
                             @JsonProperty("preco")
                             @NotNull
                             @PositiveOrZero
                             BigDecimal preco,
                             @Schema(example = "true", required = true)
                             @JsonProperty("ativo")
                             @NotNull
                             Boolean ativo) {
}
