package com.food.infrastructure.service;

import com.food.api.model.response.PermissaoResponse;
import com.food.domain.exception.PermissaoNaoEncontradaException;
import com.food.domain.repository.PermissaoRepository;
import com.food.service.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissaoServiceImpl implements PermissaoService {

    private final PermissaoRepository permissaoRepository;

    @Autowired
    public PermissaoServiceImpl(PermissaoRepository permissaoRepository) {
        this.permissaoRepository = permissaoRepository;
    }

    @Override
    public PermissaoResponse buscarOuFalhar(Long permissaoId) {
        return permissaoRepository.findById(permissaoId)
                .map(PermissaoResponse::new)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
    }
}
