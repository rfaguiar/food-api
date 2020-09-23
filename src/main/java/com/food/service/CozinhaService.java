package com.food.service;

import com.food.api.model.request.CozinhaRequest;
import com.food.api.model.response.CozinhaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CozinhaService {
    Page<CozinhaResponse> todas(Pageable pageable);

    CozinhaResponse buscarPorId(Long cozinhaId);

    CozinhaResponse salvar(CozinhaRequest cozinha);

    CozinhaResponse atualizar(Long cozinhaId, CozinhaRequest CozinhaResponse);

    void remover(Long cozinhaId);
}
