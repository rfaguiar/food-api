package com.food.infrastructure.service;

import com.food.domain.exception.FormaPagamentoNaoEncontradaException;
import com.food.domain.model.FormaPagamento;
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

    @Override
    public FormaPagamentoDto buscarPorId(Long id) {
        return new FormaPagamentoDto(buscarPorIdEValidar(id));
    }

    @Override
    public FormaPagamentoDto cadastrar(FormaPagamentoDto dto) {
        return new FormaPagamentoDto(formaPagamentoRepository.save(new FormaPagamento(null, dto.descricao())));
    }

    @Override
    public FormaPagamentoDto atualizar(Long id, FormaPagamentoDto dto) {
        FormaPagamento antigo = buscarPorIdEValidar(id);
        FormaPagamento novo = formaPagamentoRepository.save(new FormaPagamento(antigo.id(), dto.descricao()));
        return new FormaPagamentoDto(novo);
    }

    @Override
    public void remover(Long id) {
        formaPagamentoRepository.delete(buscarPorIdEValidar(id));
    }

    private FormaPagamento buscarPorIdEValidar(Long id) {
        return formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
    }
}
