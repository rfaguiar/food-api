package com.food.domain.repository;

import com.food.domain.model.Cozinha;

import java.util.stream.Stream;

public interface CozinhaRepository {
    Stream<Cozinha> todas();
    Cozinha adicionar(Cozinha cozinha);
    Cozinha porId(Long idCozinha);
    void remover(Cozinha cozinha);
}
