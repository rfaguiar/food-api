package com.food.service;

import com.food.service.model.EstadoDto;

import java.util.List;

public interface EstadoService {
    List<EstadoDto> todos();
    EstadoDto buscarPorId(Long estadoId);
    EstadoDto adicionar(EstadoDto estado);
    EstadoDto atualizar(Long estadoId, EstadoDto estadoDto);
    void remover(Long estadoId);
}
