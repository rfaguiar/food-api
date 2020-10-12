package com.food.service;

import com.food.api.model.request.FormaPagamentoRequest;
import com.food.domain.model.FormaPagamento;

import java.util.List;

public interface FormaPagamentoService {
    List<FormaPagamento> buscarFormaPagamento();

    FormaPagamento buscarPorId(Long id);

    FormaPagamento cadastrar(FormaPagamentoRequest dto);

    FormaPagamento atualizar(Long id, FormaPagamentoRequest dto);

    void remover(Long id);
}
