package com.food.domain.repository;

import com.food.domain.model.Cozinha;

import java.util.Optional;
import java.util.stream.Stream;

public interface CozinhaRepository {
    Stream<Cozinha> todas();
    Cozinha adicionar(Cozinha cozinha);
    Optional<Cozinha> porId(Long idCozinha);
    Cozinha remover(Cozinha cozinha);
}
