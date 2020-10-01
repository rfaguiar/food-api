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
        this(pedido.getCodigo(), pedido.getSubtotal(), pedido.getTaxaFrete(), pedido.getValorTotal(),
                pedido.getStatus().name(), pedido.getDataCriacao(), pedido.getDataConfirmacao(),
                pedido.getDataEntrega(), pedido.getDataCancelamento(),
                new RestauranteResumoResponse(pedido.getRestaurante()),
                new UsuarioResponse(pedido.getCliente()),
                new FormaPagamentoResponse(pedido.getFormaPagamento()),
                new EnderecoResponse(pedido.getEnderecoEntrega()),
                pedido.getItens().stream().map(ItemPedidoResponse::new).collect(Collectors.toList())
        );
    }
}
