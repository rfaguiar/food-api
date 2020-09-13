package com.food.service;

import com.food.api.model.request.CidadeRequest;
import com.food.api.model.response.CidadeResponse;

import java.util.List;

public interface CidadeService {
    List<CidadeResponse> todos();
    CidadeResponse buscarPorId(Long cidadeId);
    CidadeResponse adicionar(CidadeRequest cidade);
    CidadeResponse atualizar(Long cidadeId, CidadeRequest cidade);
    void remover(Long cidadeId);
}
