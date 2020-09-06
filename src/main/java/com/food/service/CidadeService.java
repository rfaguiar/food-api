package com.food.service;

import com.food.service.model.CidadeDto;

import java.util.List;

public interface CidadeService {
    List<CidadeDto> todos();
    CidadeDto buscarPorId(Long cidadeId);
    CidadeDto adicionar(CidadeDto cidade);
    CidadeDto atualizar(Long cidadeId, CidadeDto cidade);
    void remover(Long cidadeId);
}
