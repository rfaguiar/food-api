package com.food.service;

import com.food.service.model.EstadoDto;

import java.util.List;
import java.util.Optional;

public interface EstadoService {
    List<EstadoDto> todos();
    Optional<EstadoDto> buscarPorId(Long estadoId);
    Optional<EstadoDto> adicionar(EstadoDto estado);
    Optional<EstadoDto> atualizar(Long estadoId, EstadoDto estadoDto);
    void remover(Long estadoId);
}
