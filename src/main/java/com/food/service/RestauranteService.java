package com.food.service;

import com.food.service.model.RestauranteDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface RestauranteService {
    List<RestauranteDto> todos();

    RestauranteDto buscarPorId(Long restauranteId);

    RestauranteDto adicionar(RestauranteDto restaurante);

    RestauranteDto atualizar(Long restauranteId, RestauranteDto restaurante);

    RestauranteDto atualizarParcial(Long restauranteId, Map<String, Object> campos, HttpServletRequest request);
}
