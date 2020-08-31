package com.food.infrastructure.repository;

import com.food.domain.model.FormaPagamento;
import com.food.domain.repository.FormaPagamentoRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.stream.Stream;

@Repository
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Stream<FormaPagamento> todas() {
        return manager.createQuery("from FormaPagamento", FormaPagamento.class)
                .getResultStream();
    }

    @Override
    @Transactional
    public FormaPagamento adicionar(FormaPagamento pagamento) {
        return manager.merge(pagamento);
    }

    @Override
    public FormaPagamento porId(Long id) {
        return manager.find(FormaPagamento.class, id);
    }

    @Override
    @Transactional
    public void remover(FormaPagamento formaPagamento) {
        formaPagamento = porId(formaPagamento.id());
        manager.remove(formaPagamento);
    }
}
