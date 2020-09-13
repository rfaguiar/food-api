package com.food.infrastructure.service;

import com.food.api.model.request.CidadeRequest;
import com.food.api.model.response.CidadeResponse;
import com.food.domain.exception.CidadeNaoEncontradaException;
import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.exception.NegocioException;
import com.food.domain.model.Cidade;
import com.food.domain.model.Estado;
import com.food.domain.repository.CidadeRepository;
import com.food.domain.repository.EstadoRepository;
import com.food.service.CidadeService;
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
    public List<CidadeResponse> todos() {
        return cidadeRepository.findAll().stream()
                .map(CidadeResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public CidadeResponse buscarPorId(Long id) {
        Cidade cidade = buscarPorIdEValidar(id);
        return new CidadeResponse(cidade);
    }

    @Override
    public CidadeResponse adicionar(CidadeRequest dto) {
        Estado estado = validarEstado(dto.estado());
        Cidade cidade = new Cidade(null, dto.nome(), estado);
        return new CidadeResponse(cidadeRepository.save(cidade));
    }

    @Override
    public CidadeResponse atualizar(Long cidadeId, CidadeRequest dto) {
        Cidade antigo = buscarPorIdEValidar(cidadeId);
        Estado estado = validarEstado(dto.estado());
        return new CidadeResponse(cidadeRepository.save(new Cidade(antigo.id(), dto.nome(), estado)));
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
                .orElseThrow(() -> new NegocioException(
                        MessageFormat.format("Não existe um cadastro de estado com código {0}",
                                dto.id())));
    }

    private Cidade buscarPorIdEValidar(Long id) {
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }
}
