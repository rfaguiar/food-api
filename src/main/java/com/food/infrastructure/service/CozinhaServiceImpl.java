package com.food.infrastructure.service;

import com.food.domain.model.Cozinha;
import com.food.domain.repository.CozinhaRepository;
import com.food.service.CozinhaService;
import com.food.service.model.CozinhaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<CozinhaDTO> todas() {
        return cozinhaRepository.todas()
                .map(CozinhaDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CozinhaDTO> buscarPorId(Long cozinhaId) {
        return Optional.ofNullable(cozinhaRepository.porId(cozinhaId))
                .map(CozinhaDTO::new);
    }

    @Override
    public Optional<CozinhaDTO> salvar(CozinhaDTO cozinha) {
        return Optional.ofNullable(cozinhaRepository.adicionar(new Cozinha(cozinha.id(), cozinha.nome())))
                .map(CozinhaDTO::new);
    }
}
