package com.food.service;

import com.food.api.model.request.EstadoRequest;
import com.food.api.model.response.EstadoResponse;

import java.util.List;

public interface EstadoService {
    List<EstadoResponse> todos();
    EstadoResponse buscarPorId(Long estadoId);
    EstadoResponse adicionar(EstadoRequest estado);
    EstadoResponse atualizar(Long estadoId, EstadoRequest estadoDto);
    void remover(Long estadoId);
}
