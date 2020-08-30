package com.food.infrastructure.repository;

import com.food.domain.model.Cidade;
import com.food.domain.repository.CidadeRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CidadeRepositoryImpl implements CidadeRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Cidade> todas() {
        return manager.createQuery("from Cidade", Cidade.class)
                .getResultList();
    }

    @Override
    @Transactional
    public Cidade adicionar(Cidade cidade) {
        return manager.merge(cidade);
    }

    @Override
    public Cidade porId(Long id) {
        return manager.find(Cidade.class, id);
    }

    @Override
    @Transactional
    public void remover(Cidade cidade) {
        cidade = porId(cidade.id());
        manager.remove(cidade);
    }
}
