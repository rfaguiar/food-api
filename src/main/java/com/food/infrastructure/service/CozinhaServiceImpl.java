package com.food.infrastructure.service;

import com.food.api.v1.model.request.CozinhaRequest;
import com.food.domain.exception.CozinhaNaoEncontradaException;
import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.model.Cozinha;
import com.food.domain.repository.CozinhaRepository;
import com.food.service.CozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class CozinhaServiceImpl implements CozinhaService {

    private final CozinhaRepository cozinhaRepository;

    @Autowired
    public CozinhaServiceImpl(CozinhaRepository cozinhaRepository) {
        this.cozinhaRepository = cozinhaRepository;
    }

    @Override
    public Page<Cozinha> todas(Pageable pageable) {
        return cozinhaRepository.findAll(pageable);
    }

    @Override
    public Cozinha buscarPorId(Long cozinhaId) {
        return buscarPorIdEValidar(cozinhaId);
    }

    @Override
    public Cozinha salvar(CozinhaRequest cozinha) {
        return cozinhaRepository.save(new Cozinha(null, cozinha.nome(), null));
    }

    @Override
    public Cozinha atualizar(Long cozinhaId, CozinhaRequest CozinhaResponse) {
        Cozinha cozinhaDestino = buscarPorIdEValidar(cozinhaId);
        return cozinhaRepository.save(new Cozinha(cozinhaDestino.getId(), CozinhaResponse.nome(), cozinhaDestino.getRestaurantes()));
    }

    @Override
    public void remover(Long cozinhaId) {
        Cozinha cozinha = buscarPorIdEValidar(cozinhaId);
        try {
            cozinhaRepository.delete(cozinha);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    MessageFormat.format("Cozinha de código {0} não pode ser removida, pois está em uso",
                            cozinhaId));
        }
    }

    private Cozinha buscarPorIdEValidar(Long cozinhaId) {
        return cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
    }
}
