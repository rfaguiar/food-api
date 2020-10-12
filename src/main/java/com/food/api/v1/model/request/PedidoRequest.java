package com.food.api.v1.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import java.util.List;

public record PedidoRequest(
                            @JsonProperty("restaurante")
                            @Valid
                            RestauranteIdRequest restaurante,
                            @JsonProperty("formaPagamento")
                            @Valid
                            FormaPagamentoIdRequest formaPagamento,
                            @JsonProperty("enderecoEntrega")
                            @Valid
                            EnderecoRequest enderecoEntrega,
                            @JsonProperty("itens")
                            @Valid
                            List<ItemPedidoRequest> itens) {
}
