package com.food.service;

import com.food.api.model.request.RestauranteRequest;
import com.food.api.model.response.RestauranteResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface RestauranteService {
    List<RestauranteResponse> todos();

    RestauranteResponse buscarPorId(Long restauranteId);

    RestauranteResponse adicionar(RestauranteRequest restaurante);

    RestauranteResponse atualizar(Long restauranteId, RestauranteRequest restaurante);

    RestauranteResponse atualizarParcial(Long restauranteId, Map<String, Object> campos, HttpServletRequest request);

    RestauranteResponse ativar(Long id);

    RestauranteResponse inativar(Long id);
}
