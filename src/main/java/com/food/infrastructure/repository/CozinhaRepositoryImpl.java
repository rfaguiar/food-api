package com.food.infrastructure.repository;

import com.food.domain.model.Cozinha;
import com.food.domain.repository.CozinhaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class CozinhaRepositoryImpl implements CozinhaRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Stream<Cozinha> todas() {
        return manager.createQuery("from Cozinha", Cozinha.class)
                .getResultStream();
    }

    @Override
    @Transactional
    public Cozinha adicionar(Cozinha cozinha) {
        return manager.merge(cozinha);
    }

    @Override
    public Optional<Cozinha> porId(Long idCozinha) {
        return Optional.ofNullable(manager.find(Cozinha.class, idCozinha));
    }

    @Override
    @Transactional
    public void remover(Cozinha cozinha) {
        cozinha = porId(cozinha.id()).get();
        manager.remove(cozinha);
    }
}
