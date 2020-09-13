package com.food.infrastructure.service;

import com.food.api.model.request.EstadoRequest;
import com.food.api.model.response.EstadoResponse;
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
import java.util.stream.Collectors;

@Service
public class EstadoServiceImpl implements EstadoService {

    private final EstadoRepository estadoRepository;

    @Autowired
    public EstadoServiceImpl(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    @Override
    public List<EstadoResponse> todos() {
        return estadoRepository.findAll().stream()
                .map(EstadoResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public EstadoResponse buscarPorId(Long id) {
        Estado estado = buscarPorIdEValidar(id);
        return new EstadoResponse(estado);
    }

    @Override
    public EstadoResponse adicionar(EstadoRequest dto) {
        return new EstadoResponse(estadoRepository.save(new Estado(null, dto.nome())));
    }

    @Override
    public EstadoResponse atualizar(Long estadoId, EstadoRequest dto) {
        Estado antigo = buscarPorIdEValidar(estadoId);
        Estado novo = estadoRepository.save(new Estado(antigo.id(), dto.nome()));
        return new EstadoResponse(novo);
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
