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
        this(pedido.getCodigo(), pedido.getSubtotal(), pedido.getTaxaFrete(), pedido.getValorTotal(),
                pedido.getStatus().name(), pedido.getDataCriacao(),
                new RestauranteResumoResponse(pedido.getRestaurante()),
                new UsuarioResponse(pedido.getCliente())
        );
    }
}
