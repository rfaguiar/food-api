package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.ItemPedido;

import java.math.BigDecimal;

public record ItemPedidoResponse(@JsonProperty("produtoId") Long produtoId,
                                 @JsonProperty("produtoNome") String produtoNome,
                                 @JsonProperty("quantidade") Integer quantidade,
                                 @JsonProperty("precoUnitario") BigDecimal precoUnitario,
                                 @JsonProperty("precoTotal") BigDecimal precoTotal,
                                 @JsonProperty("observacao") String observacao) {

    public ItemPedidoResponse(ItemPedido itemPedido) {
        this(itemPedido.produto().id(), itemPedido.produto().nome(), itemPedido.quantidade(), itemPedido.precoUnitario(),
                itemPedido.precoTotal(), itemPedido.observacao());
    }
}
