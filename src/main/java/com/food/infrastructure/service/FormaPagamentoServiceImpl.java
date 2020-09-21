package com.food.infrastructure.service;

import com.food.api.model.request.FormaPagamentoRequest;
import com.food.domain.exception.FormaPagamentoNaoEncontradaException;
import com.food.domain.model.FormaPagamento;
import com.food.domain.repository.FormaPagamentoRepository;
import com.food.service.FormaPagamentoService;
import com.food.api.model.response.FormaPagamentoResponse;
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
    public List<FormaPagamentoResponse> buscarFormaPagamento() {
        return formaPagamentoRepository.findAll()
                .stream()
                .map(FormaPagamentoResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public FormaPagamentoResponse buscarPorId(Long id) {
        return new FormaPagamentoResponse(buscarPorIdEValidar(id));
    }

    @Override
    public FormaPagamentoResponse cadastrar(FormaPagamentoRequest dto) {
        return new FormaPagamentoResponse(formaPagamentoRepository.save(new FormaPagamento(null, dto.descricao())));
    }

    @Override
    public FormaPagamentoResponse atualizar(Long id, FormaPagamentoRequest dto) {
        FormaPagamento antigo = buscarPorIdEValidar(id);
        FormaPagamento novo = formaPagamentoRepository.save(new FormaPagamento(antigo.id(), dto.descricao()));
        return new FormaPagamentoResponse(novo);
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
