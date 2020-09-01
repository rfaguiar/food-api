package com.food.service;

import com.food.domain.model.Cozinha;
import com.food.service.model.CozinhaDTO;

import java.util.List;
import java.util.Optional;

public interface CozinhaService {
    List<CozinhaDTO> todas();

    Optional<CozinhaDTO> buscarPorId(Long cozinhaId);

    Optional<CozinhaDTO> salvar(CozinhaDTO cozinha);

    Optional<CozinhaDTO> atualizar(Long cozinhaId, CozinhaDTO cozinhaDTO);

    Optional<Cozinha> remover(Long cozinhaId);
}
