package com.food.service;

import com.food.service.model.CozinhaDto;

import java.util.List;

public interface CozinhaService {
    List<CozinhaDto> todas();

    CozinhaDto buscarPorId(Long cozinhaId);

    CozinhaDto salvar(CozinhaDto cozinha);

    CozinhaDto atualizar(Long cozinhaId, CozinhaDto cozinhaDTO);

    void remover(Long cozinhaId);
}
