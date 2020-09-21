package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PedidoResumoResponse(@JsonProperty("codigo") String codigo,
                                   @JsonProperty("subtotal") BigDecimal subtotal,
                                   @JsonProperty("taxaFrete") BigDecimal taxaFrete,
                                   @JsonProperty("valorTotal") BigDecimal valorTotal,
                                   @JsonProperty("status") String status,
                                   @JsonProperty("dataCriacao") LocalDateTime dataCriacao,
                                   @JsonProperty("restaurante") RestauranteResumoResponse restaurante,
                                   @JsonProperty("cliente") UsuarioResponse cliente) {

    public PedidoResumoResponse(Pedido pedido) {
        this(pedido.codigo(), pedido.subtotal(), pedido.taxaFrete(), pedido.valorTotal(),
                pedido.status().name(), pedido.dataCriacao(),
                new RestauranteResumoResponse(pedido.restaurante()),
                new UsuarioResponse(pedido.cliente())
        );
    }
}
