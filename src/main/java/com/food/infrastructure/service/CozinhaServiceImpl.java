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
        return cozinhaRepository.findAll().stream()
                .map(CozinhaDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CozinhaDto> buscarPorId(Long cozinhaId) {
        return cozinhaRepository.findById(cozinhaId)
                .map(CozinhaDto::new);
    }

    @Override
    public Optional<CozinhaDto> salvar(CozinhaDto cozinha) {
        return Optional.ofNullable(cozinhaRepository.save(new Cozinha(cozinha.id(), cozinha.nome(), null)))
                .map(CozinhaDto::new);
    }

    @Override
    public Optional<CozinhaDto> atualizar(Long cozinhaId, CozinhaDto cozinhaDTO) {
        return cozinhaRepository.findById(cozinhaId)
                .map(c -> cozinhaRepository.save(new Cozinha(c.id(), cozinhaDTO.nome(), null)))
                .map(CozinhaDto::new);
    }

    @Override
    public void remover(Long cozinhaId) {
        try {
            Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
                    .orElseThrow(() ->
                            new EntidadeNaoEncontradaException(
                                    MessageFormat.format("Não existe um cadastro de cozinha com código {0}",
                                            cozinhaId)));
            cozinhaRepository.delete(cozinha);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    MessageFormat.format("Cozinha de código {0} não pode ser removida, pois está em uso",
                            cozinhaId));
        }
    }
}
