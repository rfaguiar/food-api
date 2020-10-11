package com.food.service;

import com.food.api.model.request.PedidoRequest;
import com.food.domain.filter.PedidoFilter;
import com.food.domain.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PedidoService {

    Page<Pedido> buscarTodos(PedidoFilter filtro, Pageable pageable);

    Pedido buscar(String codigoPedido);

    Pedido emitirPedido(PedidoRequest pedidoRequest);

    void confirmar(String codigoPedido);

    void cancelar(String codigoPedido);

    void entregar(String codigoPedido);
}
