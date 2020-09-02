package com.food.service;

import com.food.service.model.CidadeDto;

import java.util.List;
import java.util.Optional;

public interface CidadeService {
    List<CidadeDto> todos();
    Optional<CidadeDto> buscarPorId(Long cidadeId);
    Optional<CidadeDto> adicionar(CidadeDto cidade);
    Optional<CidadeDto> atualizar(Long cidadeId, CidadeDto cidade);
    void remover(Long cidadeId);
}
