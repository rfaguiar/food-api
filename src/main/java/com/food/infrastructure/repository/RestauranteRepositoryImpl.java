package com.food.infrastructure.repository;

import com.food.domain.model.Restaurante;
import com.food.domain.repository.RestauranteRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> todas() {
        return manager.createQuery("from Restaurante", Restaurante.class)
                .getResultList();
    }

    @Override
    @Transactional
    public Restaurante adicionar(Restaurante restaurante) {
        return manager.merge(restaurante);
    }

    @Override
    public Restaurante porId(Long id) {
        return manager.find(Restaurante.class, id);
    }

    @Override
    @Transactional
    public void remover(Restaurante restaurante) {
        restaurante = porId(restaurante.id());
        manager.remove(restaurante);
    }
}
