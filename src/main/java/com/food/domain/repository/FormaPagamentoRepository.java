package com.food.domain.repository;

import com.food.domain.model.FormaPagamento;

import javax.transaction.Transactional;
import java.util.List;

public interface FormaPagamentoRepository {
    List<FormaPagamento> todas();

    @Transactional
    FormaPagamento adicionar(FormaPagamento pagamento);

    FormaPagamento porId(Long id);

    @Transactional
    void remover(FormaPagamento formaPagamento);
}
