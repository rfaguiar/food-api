package com.food.service;

import com.food.service.model.RestauranteDto;

import java.util.List;
import java.util.Optional;

public interface RestauranteService {
    List<RestauranteDto> todos();
    Optional<RestauranteDto> buscarPorId(Long restauranteId);

    Optional<RestauranteDto> adicionar(RestauranteDto restaurante);

    Optional<RestauranteDto> atualizar(Long restauranteId, RestauranteDto restaurante);
}