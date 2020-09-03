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
import java.util.Optional;
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
    public Optional<EstadoDto> buscarPorId(Long id) {
        return estadoRepository.findById(id)
                .map(EstadoDto::new);
    }

    @Override
    public Optional<EstadoDto> adicionar(EstadoDto estado) {
        return Optional.of(estadoRepository.save(new Estado(estado.id(), estado.nome())))
                .map(EstadoDto::new);
    }

    @Override
    public Optional<EstadoDto> atualizar(Long estadoId, EstadoDto dto) {
        return estadoRepository.findById(estadoId)
                .map(e -> estadoRepository.save(new Estado(e.id(), dto.nome())))
                .map(EstadoDto::new);
    }

    @Override
    public void remover(Long estadoId) {
        try {
            estadoRepository.findById(estadoId)
                    .map(e -> {
                        estadoRepository.delete(e);
                        return e;
                    })
                    .orElseThrow(() ->
                            new EntidadeNaoEncontradaException(
                                    MessageFormat.format("Não existe um cadastro de estado com código {0}",
                                            estadoId)));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    MessageFormat.format("Estado de código {0} não pode ser removida, pois está em uso",
                            estadoId));
        }
    }
}
