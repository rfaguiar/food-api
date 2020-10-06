package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.ItemPedido;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public record ItemPedidoResponse(@ApiModelProperty(example = "1")
                                 @JsonProperty("produtoId") Long produtoId,
                                 @ApiModelProperty(example = "Porco com molho agridoce")
                                 @JsonProperty("produtoNome") String produtoNome,
                                 @ApiModelProperty(example = "2")
                                 @JsonProperty("quantidade") Integer quantidade,
                                 @ApiModelProperty(example = "78.90")
                                 @JsonProperty("precoUnitario") BigDecimal precoUnitario,
                                 @ApiModelProperty(example = "157.80")
                                 @JsonProperty("precoTotal") BigDecimal precoTotal,
                                 @ApiModelProperty(example = "Menos picante, por favor")
                                 @JsonProperty("observacao") String observacao) {

    public ItemPedidoResponse(ItemPedido itemPedido) {
        this(itemPedido.produto().id(), itemPedido.produto().nome(), itemPedido.quantidade(), itemPedido.precoUnitario(),
                itemPedido.precoTotal(), itemPedido.observacao());
    }
}
