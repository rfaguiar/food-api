package com.food.infrastructure.repository;

import com.food.domain.model.Restaurante;
import com.food.domain.repository.RestauranteRepositoryQueries;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.stream.Stream;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Stream<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        var jpql = "from Restaurante " +
                " where 0=0 " +
                " and (:nome is null or nome like :nome) " +
                " and (:taxaInicial is null or taxaFrete >= :taxaInicial)" +
                " and (:taxaFinal is null or taxaFrete <= :taxaFinal) ";

        return manager.createQuery(jpql, Restaurante.class)
                .setParameter("taxaFinal", taxaFreteFinal)
                .setParameter("taxaInicial", taxaFreteInicial)
                .setParameter("nome", "%" + nome + "%")
                .getResultStream();
    }

}