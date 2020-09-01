package com.food.domain.repository;

import com.food.domain.model.Restaurante;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Stream;

public interface RestauranteRepository {
    Stream<Restaurante> todos();

    @Transactional
    Restaurante adicionar(Restaurante restaurante);

    Optional<Restaurante> porId(Long id);

    @Transactional
    Restaurante remover(Restaurante restaurante);
}
