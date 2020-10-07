package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Produto;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public record ProdutoResponse(@ApiModelProperty(example = "1")
                              @JsonProperty("id") Long id,
                              @ApiModelProperty(example = "Espetinho de Cupim")
                              @JsonProperty("nome") String nome,
                              @ApiModelProperty(example = "Acompanha farinha, mandioca e vinagrete")
                              @JsonProperty("descricao") String descricao,
                              @ApiModelProperty(example = "12.50")
                              @JsonProperty("preco") BigDecimal preco,
                              @ApiModelProperty(example = "true")
                              @JsonProperty("ativo") Boolean ativo) {

    public ProdutoResponse(Produto produto) {
        this(produto.id(), produto.nome(), produto.descricao(), produto.preco(), produto.ativo());
    }
}
