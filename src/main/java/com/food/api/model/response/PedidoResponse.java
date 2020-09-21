package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PedidoResponse(@JsonProperty("codigo") String codigo,
                             @JsonProperty("subtotal") BigDecimal subtotal,
                             @JsonProperty("taxaFrete") BigDecimal taxaFrete,
                             @JsonProperty("valorTotal") BigDecimal valorTotal,
                             @JsonProperty("status") String status,
                             @JsonProperty("dataCriacao") LocalDateTime dataCriacao,
                             @JsonProperty("dataConfirmacao") LocalDateTime dataConfirmacao,
                             @JsonProperty("dataEntrega") LocalDateTime dataEntrega,
                             @JsonProperty("dataCancelamento") LocalDateTime dataCancelamento,
                             @JsonProperty("restaurante") RestauranteResumoResponse restaurante,
                             @JsonProperty("cliente") UsuarioResponse cliente,
                             @JsonProperty("formaPagamento") FormaPagamentoResponse formaPagamento,
                             @JsonProperty("enderecoEntrega") EnderecoResponse enderecoEntrega,
                             @JsonProperty("itens") List<ItemPedidoResponse> itens) {

    public PedidoResponse(Pedido pedido) {
        this(pedido.codigo(), pedido.subtotal(), pedido.taxaFrete(), pedido.valorTotal(),
                pedido.status().name(), pedido.dataCriacao(), pedido.dataConfirmacao(),
                pedido.dataEntrega(), pedido.dataCancelamento(),
                new RestauranteResumoResponse(pedido.restaurante()),
                new UsuarioResponse(pedido.cliente()),
                new FormaPagamentoResponse(pedido.formaPagamento()),
                new EnderecoResponse(pedido.enderecoEntrega()),
                pedido.itens().stream().map(ItemPedidoResponse::new).collect(Collectors.toList())
        );
    }
}
