package com.food.infrastructure.repository;

import com.food.domain.model.Permissao;
import com.food.domain.repository.PermissaoRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PermissaoRepositoryImpl implements PermissaoRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Permissao> todas() {
        return manager.createQuery("from Permissao", Permissao.class)
                .getResultList();
    }

    @Override
    @Transactional
    public Permissao adicionar(Permissao Permissao) {
        return manager.merge(Permissao);
    }

    @Override
    public Permissao porId(Long idPermissao) {
        return manager.find(Permissao.class, idPermissao);
    }

    @Override
    @Transactional
    public void remover(Permissao Permissao) {
        Permissao = porId(Permissao.id());
        manager.remove(Permissao);
    }
}
