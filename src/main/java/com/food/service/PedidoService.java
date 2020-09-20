package com.food.service;

import com.food.api.model.response.PedidoResponse;
import com.food.api.model.response.PedidoResumoResponse;

import java.util.List;

public interface PedidoService {

    List<PedidoResumoResponse> buscarTodos();

    PedidoResponse buscar(Long pedidoId);
}
