package com.food.infrastructure.repository;

import com.food.domain.model.Estado;
import com.food.domain.repository.EstadoRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class EstadoRepositoryImpl implements EstadoRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Stream<Estado> todos() {
        return manager.createQuery("from Estado", Estado.class)
                .getResultStream();
    }

    @Override
    @Transactional
    public Estado adicionar(Estado estado) {
        return manager.merge(estado);
    }

    @Override
    public Optional<Estado> porId(Long id) {
        return Optional.ofNullable(manager.find(Estado.class, id));
    }

    @Override
    @Transactional
    public Estado remover(Estado estado) {
        manager.remove(estado);
        return estado;
    }
}
