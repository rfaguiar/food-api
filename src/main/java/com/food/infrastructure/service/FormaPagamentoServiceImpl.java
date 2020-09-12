package com.food.infrastructure.service;

import com.food.domain.repository.FormaPagamentoRepository;
import com.food.service.FormaPagamentoService;
import com.food.service.model.FormaPagamentoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormaPagamentoServiceImpl implements FormaPagamentoService {

    private final FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    public FormaPagamentoServiceImpl(FormaPagamentoRepository formaPagamentoRepository) {
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    @Override
    public List<FormaPagamentoDto> buscarFormaPagamento() {
        return formaPagamentoRepository.findAll()
                .stream()
                .map(FormaPagamentoDto::new)
                .collect(Collectors.toList());
    }
}
