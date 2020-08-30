package com.food.domain.repository;

import com.food.domain.model.Restaurante;

import javax.transaction.Transactional;
import java.util.List;

public interface RestauranteRepository {
    List<Restaurante> todos();

    @Transactional
    Restaurante adicionar(Restaurante restaurante);

    Restaurante porId(Long id);

    @Transactional
    void remover(Restaurante restaurante);
}
