package com.food.service;

import com.food.api.model.request.CozinhaRequest;
import com.food.api.model.response.CozinhaResponse;

import java.util.List;

public interface CozinhaService {
    List<CozinhaResponse> todas();

    CozinhaResponse buscarPorId(Long cozinhaId);

    CozinhaResponse salvar(CozinhaRequest cozinha);

    CozinhaResponse atualizar(Long cozinhaId, CozinhaRequest CozinhaResponse);

    void remover(Long cozinhaId);
}
