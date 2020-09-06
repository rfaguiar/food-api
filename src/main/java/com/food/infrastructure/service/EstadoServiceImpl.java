package com.food.infrastructure.service;

import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.domain.model.Estado;
import com.food.domain.repository.EstadoRepository;
import com.food.service.EstadoService;
import com.food.service.model.EstadoDto;
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
    public List<EstadoDto> todos() {
        return estadoRepository.findAll().stream()
                .map(EstadoDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public EstadoDto buscarPorId(Long id) {
        Estado estado = buscarPorIdEValidar(id);
        return new EstadoDto(estado);
    }

    @Override
    public EstadoDto adicionar(EstadoDto dto) {
        return new EstadoDto(estadoRepository.save(new Estado(dto.id(), dto.nome())));
    }

    @Override
    public EstadoDto atualizar(Long estadoId, EstadoDto dto) {
        Estado antigo = buscarPorIdEValidar(estadoId);
        Estado novo = estadoRepository.save(new Estado(antigo.id(), dto.nome()));
        return new EstadoDto(novo);
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
                .orElseThrow(() ->
                        new EntidadeNaoEncontradaException(
                                MessageFormat.format("Não existe um cadastro de estado com código {0}",
                                        id)));
    }
}
