package com.food.service;

import com.food.api.model.response.PedidoResponse;

import java.util.List;

public interface PedidoService {

    List<PedidoResponse> buscarTodos();

    PedidoResponse buscar(Long pedidoId);
}
