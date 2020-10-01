package com.food.domain.event;

import com.food.domain.model.Pedido;

public record PedidoCanceladoEvent(Pedido pedido) {
}
