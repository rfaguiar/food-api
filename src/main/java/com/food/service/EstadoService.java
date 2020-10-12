package com.food.service;

import com.food.api.v1.model.request.EstadoRequest;
import com.food.domain.model.Estado;

import java.util.List;

public interface EstadoService {
    List<Estado> todos();
    Estado buscarPorId(Long estadoId);
    Estado adicionar(EstadoRequest estado);
    Estado atualizar(Long estadoId, EstadoRequest estadoDto);
    void remover(Long estadoId);
}
