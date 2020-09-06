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
    public CidadeDto buscarPorId(Long id) {
        Cidade cidade = buscarPorIdEValidar(id);
        return new CidadeDto(cidade);
    }

    @Override
    public CidadeDto adicionar(CidadeDto dto) {
        Estado estado = validarEstado(dto.estado());
        return new CidadeDto(cidadeRepository.save(
                new Cidade(dto.id(), dto.nome(), estado)));
    }

    @Override
    public CidadeDto atualizar(Long cidadeId, CidadeDto dto) {
        Estado estado = validarEstado(dto.estado());
        Cidade antigo = buscarPorIdEValidar(cidadeId);
        return new CidadeDto(cidadeRepository.save(new Cidade(antigo.id(), dto.nome(), estado)));
    }

    @Override
    public void remover(Long cidadeId) {
        Cidade cidade = buscarPorIdEValidar(cidadeId);
        try {
            cidadeRepository.delete(cidade);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    MessageFormat.format("Cidade de código {0} não pode ser removida, pois está em uso",
                            cidadeId));
        }
    }

    private Estado validarEstado(EstadoDto dto) {
        return estadoRepository.findById(dto.id())
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        MessageFormat.format("Não existe um cadastro de estado com código {0}",
                                dto.id())));
    }

    private Cidade buscarPorIdEValidar(Long id) {
        return cidadeRepository.findById(id)
                .orElseThrow(() ->
                        new EntidadeNaoEncontradaException(
                                MessageFormat.format("Não existe cadastro de cidade com código {0}",
                                        id)));
    }
}
