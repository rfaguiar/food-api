package com.food.infrastructure.service;

import com.food.api.v1.model.request.CidadeRequest;
import com.food.domain.exception.CidadeNaoEncontradaException;
import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.exception.NegocioException;
import com.food.domain.model.Cidade;
import com.food.domain.model.Estado;
import com.food.domain.repository.CidadeRepository;
import com.food.domain.repository.EstadoRepository;
import com.food.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

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
    public List<Cidade> todos() {
        return cidadeRepository.findAll();
    }

    @Override
    public Cidade buscarPorId(Long id) {
        return buscarPorIdEValidar(id);
    }

    @Override
    public Cidade adicionar(CidadeRequest dto) {
        Estado estado = validarEstado(dto.estado().id());
        Cidade cidade = new Cidade(null, dto.nome(), estado);
        return cidadeRepository.save(cidade);
    }

    @Override
    public Cidade atualizar(Long cidadeId, CidadeRequest dto) {
        Cidade antigo = buscarPorIdEValidar(cidadeId);
        Estado estado = validarEstado(dto.estado().id());
        return cidadeRepository.save(new Cidade(antigo.id(), dto.nome(), estado));
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

    private Estado validarEstado(Long id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new NegocioException(
                        MessageFormat.format("Não existe um cadastro de estado com código {0}", id)));
    }

    Cidade buscarPorIdEValidar(Long id) {
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }
}
