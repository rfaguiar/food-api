package com.food.infrastructure.service;

import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.domain.model.Cozinha;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.CozinhaRepository;
import com.food.domain.repository.RestauranteRepository;
import com.food.service.RestauranteService;
import com.food.service.model.RestauranteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestauranteServiceImpl implements RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final CozinhaRepository cozinhaRepository;

    @Autowired
    public RestauranteServiceImpl(RestauranteRepository restauranteRepository, CozinhaRepository cozinhaRepository) {
        this.restauranteRepository = restauranteRepository;
        this.cozinhaRepository = cozinhaRepository;
    }

    @Override
    public List<RestauranteDto> todos() {
        return restauranteRepository.todos()
                .map(RestauranteDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RestauranteDto> buscarPorId(Long restauranteId) {
        return restauranteRepository.porId(restauranteId)
                .map(RestauranteDto::new);
    }

    @Override
    public Optional<RestauranteDto> adicionar(RestauranteDto dto) {
        Cozinha cozinha = cozinhaRepository.porId(dto.cozinha().id())
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        MessageFormat.format("Não existe cadastro de cozinha com código {0}",
                                dto.cozinha().id())));

        return Optional.ofNullable(
                restauranteRepository.adicionar(
                        new Restaurante(dto.id(), dto.nome(), dto.taxaFrete(), cozinha)))
                .map(RestauranteDto::new);
    }
}
