package com.food.api.v1.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record ProdutoRequest(@ApiModelProperty(example = "Espetinho de Cupim", required = true)
                             @JsonProperty("nome")
                             @NotBlank
                             String nome,
                             @ApiModelProperty(example = "Acompanha farinha, mandioca e vinagrete", required = true)
                             @JsonProperty("descricao")
                             @NotBlank
                             String descricao,
                             @ApiModelProperty(example = "12.50", required = true)
                             @JsonProperty("preco")
                             @NotNull
                             @PositiveOrZero
                             BigDecimal preco,
                             @ApiModelProperty(example = "true", required = true)
                             @JsonProperty("ativo")
                             @NotNull
                             Boolean ativo) {
}
