package com.food.infrastructure.service;

import com.food.domain.repository.CozinhaRepository;
import com.food.service.CozinhaService;
import com.food.service.model.CozinhaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<CozinhaDTO> todas() {
        return cozinhaRepository.todas()
                .stream()
                .map(CozinhaDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public CozinhaDTO buscarPorId(Long cozinhaId) {
        return new CozinhaDTO(cozinhaRepository.porId(cozinhaId));
    }
}
