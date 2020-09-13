package com.food.service;

import com.food.api.model.request.FormaPagamentoRequest;
import com.food.api.model.response.FormaPagamentoResponse;

import java.util.List;

public interface FormaPagamentoService {
    List<FormaPagamentoResponse> buscarFormaPagamento();

    FormaPagamentoResponse buscarPorId(Long id);

    FormaPagamentoResponse cadastrar(FormaPagamentoRequest dto);

    FormaPagamentoResponse atualizar(Long id, FormaPagamentoRequest dto);

    void remover(Long id);
}
