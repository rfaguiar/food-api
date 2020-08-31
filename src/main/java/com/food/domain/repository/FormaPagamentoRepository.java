package com.food.domain.repository;

import com.food.domain.model.FormaPagamento;

import javax.transaction.Transactional;
import java.util.stream.Stream;

public interface FormaPagamentoRepository {
    Stream<FormaPagamento> todas();

    @Transactional
    FormaPagamento adicionar(FormaPagamento pagamento);

    FormaPagamento porId(Long id);

    @Transactional
    void remover(FormaPagamento formaPagamento);
}
