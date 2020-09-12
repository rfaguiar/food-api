package com.food.service;

import com.food.service.model.FormaPagamentoDto;

import java.util.List;

public interface FormaPagamentoService {
    List<FormaPagamentoDto> buscarFormaPagamento();

    FormaPagamentoDto buscarPorId(Long id);
}
