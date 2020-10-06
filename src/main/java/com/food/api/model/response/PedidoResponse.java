package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Pedido;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PedidoResponse(@ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
                             @JsonProperty("codigo") String codigo,
                             @ApiModelProperty(example = "298.90")
                             @JsonProperty("subtotal") BigDecimal subtotal,
                             @ApiModelProperty(example = "10.00")
                             @JsonProperty("taxaFrete") BigDecimal taxaFrete,
                             @ApiModelProperty(example = "308.90")
                             @JsonProperty("valorTotal") BigDecimal valorTotal,
                             @ApiModelProperty(example = "CRIADO")
                             @JsonProperty("status") String status,
                             @ApiModelProperty(example = "2019-12-01T20:34:04Z")
                             @JsonProperty("dataCriacao") LocalDateTime dataCriacao,
                             @ApiModelProperty(example = "2019-12-01T20:35:10Z")
                             @JsonProperty("dataConfirmacao") LocalDateTime dataConfirmacao,
                             @ApiModelProperty(example = "2019-12-01T20:55:30Z")
                             @JsonProperty("dataEntrega") LocalDateTime dataEntrega,
                             @ApiModelProperty(example = "2019-12-01T20:35:00Z")
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
