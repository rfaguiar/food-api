package com.food.infrastructure.service;

import com.food.api.model.request.CozinhaRequest;
import com.food.api.model.response.CozinhaResponse;
import com.food.domain.exception.CozinhaNaoEncontradaException;
import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.model.Cozinha;
import com.food.domain.repository.CozinhaRepository;
import com.food.service.CozinhaService;
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
    public List<CozinhaResponse> todas() {
        return cozinhaRepository.findAll().stream()
                .map(CozinhaResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public CozinhaResponse buscarPorId(Long cozinhaId) {
        Cozinha cozinha = buscarPorIdEValidar(cozinhaId);
        return new CozinhaResponse(cozinha);
    }

    @Override
    public CozinhaResponse salvar(CozinhaRequest cozinha) {
        Cozinha cozinhaSalva = cozinhaRepository.save(new Cozinha(null, cozinha.nome(), null));
        return new CozinhaResponse(cozinhaSalva);
    }

    @Override
    public CozinhaResponse atualizar(Long cozinhaId, CozinhaRequest CozinhaResponse) {
        Cozinha cozinhaDestino = buscarPorIdEValidar(cozinhaId);
        Cozinha cozinhaAtualizada = cozinhaRepository.save(new Cozinha(cozinhaDestino.id(), CozinhaResponse.nome(), null));
        return new CozinhaResponse(cozinhaAtualizada);
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
