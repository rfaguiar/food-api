package com.food.infrastructure.service;

import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.model.Cozinha;
import com.food.domain.repository.CozinhaRepository;
import com.food.service.CozinhaService;
import com.food.service.model.CozinhaDTO;
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
    public List<CozinhaDTO> todas() {
        return cozinhaRepository.todas()
                .map(CozinhaDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CozinhaDTO> buscarPorId(Long cozinhaId) {
        return cozinhaRepository.porId(cozinhaId)
                .map(CozinhaDTO::new);
    }

    @Override
    public Optional<CozinhaDTO> salvar(CozinhaDTO cozinha) {
        return Optional.ofNullable(cozinhaRepository.adicionar(new Cozinha(cozinha.id(), cozinha.nome())))
                .map(CozinhaDTO::new);
    }

    @Override
    public Optional<CozinhaDTO> atualizar(Long cozinhaId, CozinhaDTO cozinhaDTO) {
        return cozinhaRepository.porId(cozinhaId)
                .map(c -> cozinhaRepository.adicionar(new Cozinha(c.id(), cozinhaDTO.nome())))
                .map(CozinhaDTO::new);
    }

    @Override
    public Optional<Cozinha> remover(Long cozinhaId) {
        try {
            return cozinhaRepository.porId(cozinhaId)
                    .map(cozinhaRepository::remover);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    MessageFormat.format("Cozinha de código {0} não pode ser removida, pois está em uso",
                            cozinhaId));
        }
    }
}
