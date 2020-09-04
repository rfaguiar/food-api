package com.food.domain.repository;

import com.food.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.stream.Stream;

public interface RestauranteRepositoryQueries {
    Stream<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
}
