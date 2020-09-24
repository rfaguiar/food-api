package com.food.service;

import com.food.api.model.request.PedidoRequest;
import com.food.api.model.response.PedidoResponse;
import com.food.api.model.response.PedidoResumoResponse;
import com.food.domain.filter.PedidoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PedidoService {

    Page<PedidoResumoResponse> buscarTodos(PedidoFilter filtro, Pageable pageable);

    PedidoResponse buscar(String codigoPedido);

    PedidoResponse emitirPedido(PedidoRequest pedidoRequest);

    void confirmar(String codigoPedido);

    void cancelar(String codigoPedido);

    void entregar(String codigoPedido);
}
