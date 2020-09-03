package com.food.infrastructure.service;

import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.domain.model.Cidade;
import com.food.domain.model.Estado;
import com.food.domain.repository.CidadeRepository;
import com.food.domain.repository.EstadoRepository;
import com.food.service.CidadeService;
import com.food.service.model.CidadeDto;
import com.food.service.model.EstadoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CidadeServiceImpl implements CidadeService {

    private final CidadeRepository cidadeRepository;
    private final EstadoRepository estadoRepository;

    @Autowired
    public CidadeServiceImpl(CidadeRepository cidadeRepository, EstadoRepository estadoRepository) {
        this.cidadeRepository = cidadeRepository;
        this.estadoRepository = estadoRepository;
    }

    @Override
    public List<CidadeDto> todos() {
        return cidadeRepository.findAll().stream()
                .map(CidadeDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CidadeDto> buscarPorId(Long id) {
        return cidadeRepository.findById(id)
                .map(CidadeDto::new);
    }

    @Override
    public Optional<CidadeDto> adicionar(CidadeDto dto) {
        Estado estado = validarEstado(dto.estado());
        return Optional.of(cidadeRepository.save(
                new Cidade(dto.id(), dto.nome(), estado)))
                .map(CidadeDto::new);
    }

    @Override
    public Optional<CidadeDto> atualizar(Long cidadeId, CidadeDto dto) {
        Estado estado = validarEstado(dto.estado());
        return cidadeRepository.findById(cidadeId)
                .map(r -> cidadeRepository.save(new Cidade(r.id(), dto.nome(), estado)))
                .map(CidadeDto::new);
    }

    @Override
    public void remover(Long cidadeId) {
        try {
            cidadeRepository.findById(cidadeId)
                    .map(c -> {
                        cidadeRepository.delete(c);
                        return c;
                    })
                    .orElseThrow(() ->
                            new EntidadeNaoEncontradaException(
                                    MessageFormat.format("Não existe um cadastro de cidade com código {0}",
                                            cidadeId)));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    MessageFormat.format("Cidade de código {0} não pode ser removida, pois está em uso",
                            cidadeId));
        }
    }

    private Estado validarEstado(EstadoDto dto) {
        return estadoRepository.findById(dto.id())
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        MessageFormat.format("Não existe cadastro de estado com código {0}",
                                dto.id())));
    }
}
