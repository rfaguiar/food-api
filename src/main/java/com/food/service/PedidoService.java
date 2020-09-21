package com.food.service;

import com.food.api.model.request.PedidoRequest;
import com.food.api.model.response.PedidoResponse;
import com.food.api.model.response.PedidoResumoResponse;

import java.util.List;

public interface PedidoService {

    List<PedidoResumoResponse> buscarTodos();

    PedidoResponse buscar(String codigoPedido);

    PedidoResponse emitirPedido(PedidoRequest pedidoRequest);

    void confirmar(String codigoPedido);

    void cancelar(String codigoPedido);

    void entregar(String codigoPedido);
}
