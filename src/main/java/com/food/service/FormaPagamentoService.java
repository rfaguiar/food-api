package com.food.service;

import com.food.service.model.FormaPagamentoDto;

import java.util.List;

public interface FormaPagamentoService {
    List<FormaPagamentoDto> buscarFormaPagamento();

    FormaPagamentoDto buscarPorId(Long id);

    FormaPagamentoDto cadastrar(FormaPagamentoDto dto);

    FormaPagamentoDto atualizar(Long id, FormaPagamentoDto dto);

    void remover(Long id);
}
