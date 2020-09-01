package com.food.infrastructure.repository;

import com.food.domain.model.Restaurante;
import com.food.domain.repository.RestauranteRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepository {

    @PersistenceContext
    private EntityManager manager;

    public Stream<Restaurante> todos() {
        return manager.createQuery("from Restaurante", Restaurante.class)
                .getResultStream();
    }

    @Override
    @Transactional
    public Restaurante adicionar(Restaurante restaurante) {
        return manager.merge(restaurante);
    }

    @Override
    public Optional<Restaurante> porId(Long id) {
        return Optional.ofNullable(manager.find(Restaurante.class, id));
    }

    @Override
    @Transactional
    public Restaurante remover(Restaurante restaurante) {
        manager.remove(restaurante);
        return restaurante;
    }
}
