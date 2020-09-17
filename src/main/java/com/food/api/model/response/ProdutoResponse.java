package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Produto;

import java.math.BigDecimal;

public record ProdutoResponse(@JsonProperty("id") Long id,
                              @JsonProperty("nome") String nome,
                              @JsonProperty("descricao") String descricao,
                              @JsonProperty("preco") BigDecimal preco,
                              @JsonProperty("ativo") Boolean ativo) {

    public ProdutoResponse(Produto produto) {
        this(produto.id(), produto.nome(), produto.descricao(), produto.preco(), produto.ativo());
    }
}
