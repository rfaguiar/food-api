package com.food.infrastructure.service;

import com.food.api.v1.model.request.FormaPagamentoRequest;
import com.food.domain.exception.FormaPagamentoNaoEncontradaException;
import com.food.domain.model.FormaPagamento;
import com.food.domain.repository.FormaPagamentoRepository;
import com.food.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormaPagamentoServiceImpl implements FormaPagamentoService {

    private final FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    public FormaPagamentoServiceImpl(FormaPagamentoRepository formaPagamentoRepository) {
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    @Override
    public List<FormaPagamento> buscarFormaPagamento() {
        return formaPagamentoRepository.findAll();
    }

    @Override
    public FormaPagamento buscarPorId(Long id) {
        return buscarPorIdEValidar(id);
    }

    @Override
    public FormaPagamento cadastrar(FormaPagamentoRequest dto) {
        return formaPagamentoRepository.save(new FormaPagamento(dto.descricao()));
    }

    @Override
    public FormaPagamento atualizar(Long id, FormaPagamentoRequest dto) {
        FormaPagamento antigo = buscarPorIdEValidar(id);
        return formaPagamentoRepository.save(new FormaPagamento(antigo.id(), dto.descricao(), null));
    }

    @Override
    public void remover(Long id) {
        formaPagamentoRepository.delete(buscarPorIdEValidar(id));
    }

    FormaPagamento buscarPorIdEValidar(Long id) {
        return formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
    }
}
