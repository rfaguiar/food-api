package com.food.service;

import com.food.api.model.request.CidadeRequest;
import com.food.domain.model.Cidade;

import java.util.List;

public interface CidadeService {
    List<Cidade> todos();
    Cidade buscarPorId(Long cidadeId);
    Cidade adicionar(CidadeRequest cidade);
    Cidade atualizar(Long cidadeId, CidadeRequest cidade);
    void remover(Long cidadeId);
}
