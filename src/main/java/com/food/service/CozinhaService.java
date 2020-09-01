package com.food.service;

import com.food.service.model.CozinhaDto;

import java.util.List;
import java.util.Optional;

public interface CozinhaService {
    List<CozinhaDto> todas();

    Optional<CozinhaDto> buscarPorId(Long cozinhaId);

    Optional<CozinhaDto> salvar(CozinhaDto cozinha);

    Optional<CozinhaDto> atualizar(Long cozinhaId, CozinhaDto cozinhaDTO);

    void remover(Long cozinhaId);
}
