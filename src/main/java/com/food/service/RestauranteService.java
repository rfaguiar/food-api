package com.food.service;

import com.food.service.model.RestauranteDto;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface RestauranteService {
    List<RestauranteDto> todos();

    RestauranteDto buscarPorId(Long restauranteId);

    List<RestauranteDto> restaurantesPorNomeFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

    RestauranteDto adicionar(RestauranteDto restaurante);

    RestauranteDto atualizar(Long restauranteId, RestauranteDto restaurante);

    RestauranteDto atualizarParcial(Long restauranteId, Map<String, Object> campos, HttpServletRequest request);

    List<RestauranteDto> restaurantesComFreteGratis(String nome);
}
