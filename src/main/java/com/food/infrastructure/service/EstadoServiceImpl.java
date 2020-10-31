package com.food.infrastructure.service;

import com.food.api.v1.model.request.EstadoRequest;
import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.exception.EstadoNaoEncontradaException;
import com.food.domain.model.Estado;
import com.food.domain.repository.EstadoRepository;
import com.food.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class EstadoServiceImpl implements EstadoService {

    private final EstadoRepository estadoRepository;

    @Autowired
    public EstadoServiceImpl(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    @Override
    public List<Estado> todos() {
        return estadoRepository.findAll();
    }

    @Override
    public Estado buscarPorId(Long id) {
        return buscarPorIdEValidar(id);
    }

    @Override
    public Estado adicionar(EstadoRequest dto) {
        return estadoRepository.save(new Estado(null, dto.nome()));
    }

    @Override
    public Estado atualizar(Long estadoId, EstadoRequest dto) {
        Estado antigo = buscarPorIdEValidar(estadoId);
       return estadoRepository.save(new Estado(antigo.getId(), dto.nome()));
    }

    @Override
    public void remover(Long estadoId) {
        Estado estado = buscarPorIdEValidar(estadoId);
        try {
            estadoRepository.delete(estado);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    MessageFormat.format("Estado de código {0} não pode ser removido, pois está em uso",
                            estadoId));
        }
    }

    private Estado buscarPorIdEValidar(Long id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new EstadoNaoEncontradaException(id));
    }
}
