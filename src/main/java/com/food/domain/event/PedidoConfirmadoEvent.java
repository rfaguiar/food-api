package com.food.domain.event;

import com.food.domain.model.Pedido;

public record PedidoConfirmadoEvent(Pedido pedido) {

    public PedidoConfirmadoEvent(Pedido pedido) {
        this.pedido = pedido;
    }
}
