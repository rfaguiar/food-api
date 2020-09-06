package com.food.infrastructure.service;

import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.domain.model.Cozinha;
import com.food.domain.repository.CozinhaRepository;
import com.food.service.CozinhaService;
import com.food.service.model.CozinhaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CozinhaServiceImpl implements CozinhaService {

    private final CozinhaRepository cozinhaRepository;

    @Autowired
    public CozinhaServiceImpl(CozinhaRepository cozinhaRepository) {
        this.cozinhaRepository = cozinhaRepository;
    }

    @Override
    public List<CozinhaDto> todas() {
        return cozinhaRepository.findAll().stream()
                .map(CozinhaDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public CozinhaDto buscarPorId(Long cozinhaId) {
        Cozinha cozinha = buscarPorIdEValidar(cozinhaId);
        return new CozinhaDto(cozinha);
    }

    @Override
    public CozinhaDto salvar(CozinhaDto cozinha) {
        Cozinha cozinhaSalva = cozinhaRepository.save(new Cozinha(cozinha.id(), cozinha.nome(), null));
        return new CozinhaDto(cozinhaSalva);
    }

    @Override
    public CozinhaDto atualizar(Long cozinhaId, CozinhaDto cozinhaDTO) {
        Cozinha cozinhaDestino = buscarPorIdEValidar(cozinhaId);
        Cozinha cozinhaAtualizada = cozinhaRepository.save(new Cozinha(cozinhaDestino.id(), cozinhaDTO.nome(), null));
        return new CozinhaDto(cozinhaAtualizada);
    }

    @Override
    public void remover(Long cozinhaId) {
        try {
            Cozinha cozinha = buscarPorIdEValidar(cozinhaId);
            cozinhaRepository.delete(cozinha);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    MessageFormat.format("Cozinha de código {0} não pode ser removida, pois está em uso",
                            cozinhaId));
        }
    }

    private Cozinha buscarPorIdEValidar(Long cozinhaId) {
        return cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() ->
                        new EntidadeNaoEncontradaException(
                                MessageFormat.format("Não existe um cadastro de cozinha com código {0}",
                                        cozinhaId)));
    }
}
