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
import java.util.Optional;
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
        return cozinhaRepository.todas()
                .map(CozinhaDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CozinhaDto> buscarPorId(Long cozinhaId) {
        return cozinhaRepository.porId(cozinhaId)
                .map(CozinhaDto::new);
    }

    @Override
    public Optional<CozinhaDto> salvar(CozinhaDto cozinha) {
        return Optional.ofNullable(cozinhaRepository.adicionar(new Cozinha(cozinha.id(), cozinha.nome())))
                .map(CozinhaDto::new);
    }

    @Override
    public Optional<CozinhaDto> atualizar(Long cozinhaId, CozinhaDto cozinhaDTO) {
        return cozinhaRepository.porId(cozinhaId)
                .map(c -> cozinhaRepository.adicionar(new Cozinha(c.id(), cozinhaDTO.nome())))
                .map(CozinhaDto::new);
    }

    @Override
    public void remover(Long cozinhaId) {
        try {
            Cozinha cozinha = cozinhaRepository.porId(cozinhaId)
                    .orElseThrow(() ->
                            new EntidadeNaoEncontradaException(
                                    MessageFormat.format("Não existe um cadastro de cozinha com código {0}",
                                            cozinhaId)));
            cozinhaRepository.remover(cozinha);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    MessageFormat.format("Cozinha de código {0} não pode ser removida, pois está em uso",
                            cozinhaId));
        }
    }
}
