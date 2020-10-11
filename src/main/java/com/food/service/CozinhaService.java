package com.food.service;

import com.food.api.model.request.CozinhaRequest;
import com.food.domain.model.Cozinha;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CozinhaService {
    Page<Cozinha> todas(Pageable pageable);

    Cozinha buscarPorId(Long cozinhaId);

    Cozinha salvar(CozinhaRequest cozinha);

    Cozinha atualizar(Long cozinhaId, CozinhaRequest CozinhaResponse);

    void remover(Long cozinhaId);
}
